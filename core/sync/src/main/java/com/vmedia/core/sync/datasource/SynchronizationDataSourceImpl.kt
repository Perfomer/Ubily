package com.vmedia.core.sync.datasource

import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.common.pure.util.rx.andThenMerge
import com.vmedia.core.sync.*
import com.vmedia.core.sync.SynchronizationDataType.PERIODS
import com.vmedia.core.sync.SynchronizationEvent.*
import com.vmedia.core.sync.cache.CachedDatabaseDataSourceDecorator
import com.vmedia.core.sync.cache.CachedNetworkDataSourceDecorator
import com.vmedia.core.sync.event.SynchronizationEventProducer
import com.vmedia.core.sync.synchronizer.MutableSynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.PublisherCredentialsSynchronizer
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import timber.log.Timber

internal class SynchronizationDataSourceImpl(
    private val networkDataSource: CachedNetworkDataSourceDecorator,
    private val databaseDataSource: CachedDatabaseDataSourceDecorator,
    private val synchronizationDataTypeProvider: SynchronizationDataTypeProvider,
    private val periodsProvider: MutableSynchronizationPeriodsProvider,
    private val eventProducer: SynchronizationEventProducer,

    private val credentialsSynchronizer: PublisherCredentialsSynchronizer,

    private val publisherSynchronizer: _PublisherSynchronizer,
    private val assetSynchronizer: _AssetSynchronizer,
    private val reviewSynchronizer: _ReviewSynchronizer,
    private val saleSynchronizer: _SaleSynchronizer,
    private val downloadSynchronizer: _DownloadSynchronizer,
    private val revenueSynchronizer: _RevenueSynchronizer,
    private val payoutSynchronizer: _PayoutSynchronizer,
    private val periodSynchronizer: _PeriodSynchronizer,
    private val userSynchronizer: _UserSynchronizer,
    private val categorySynchronizer: _CategorySynchronizer
) : SynchronizationDataSource {

    @Volatile
    override var isSynchronizing = false

    private val statusSubject: Subject<SynchronizationStatus> = BehaviorSubject.create()

    @Volatile
    private var syncStatus = SynchronizationStatus()
        set(value) {
            field = value
            statusSubject.onNext(value)
        }

    override fun getSynchronizationStatus() = statusSubject

    override fun synchronize(): Completable {
        if (isSynchronizing) {
            return Completable.error(
                IllegalStateException(
                    "There's already running synchronization. " +
                            "You can't run the second one until the first one is finished."
                )
            )
        }

        return execute()
            .andThen(Completable.defer { eventProducer.produce(syncStatus) })
            .doOnSubscribe { isSynchronizing = true }
            .doOnTerminate {
                isSynchronizing = false
                clear()
            }
    }

    private fun execute(): Completable {
        return synchronizeCredentials()
            .andThen(categorySynchronizer.synchronize())
            .andThenSynchronizeWith(
                publisherSynchronizer,
                assetSynchronizer,
                userSynchronizer
            )
            .andThen(
                periodSynchronizer.synchronize()
                    .doOnComplete(::publishPeriods)
            )
            .andThenSynchronizeWith(
                reviewSynchronizer,
                saleSynchronizer,
                downloadSynchronizer,
                revenueSynchronizer,
                payoutSynchronizer
            )
    }

    private fun synchronizeCredentials(): Completable {
        return credentialsSynchronizer.synchronize()
            .doOnError { syncStatus = syncStatus.copy(isAuthFailed = true) }
    }

    private fun clear() {
        networkDataSource.dropCache()
        databaseDataSource.dropCache()

        syncStatus = SynchronizationStatus()
    }

    @Suppress("UNCHECKED_CAST")
    private fun publishPeriods() {
        val event = syncStatus.events[PERIODS] as Data<List<Period>>
        periodsProvider.periods = event.data
    }

    private fun <T> Synchronizer<T>.synchronize(): Completable {
        return checkCancellation { syncStatus = syncStatus.update(dataType, Cancelled) }
            .flatMapSingle {
                execute()
                    .doOnSubscribe { syncStatus = syncStatus.update(dataType, Loading) }
                    .doOnSuccess { value -> syncStatus = syncStatus.update(dataType, Data(value)) }
                    .doOnError { error ->
                        Timber.e(error)
                        syncStatus = syncStatus.update(dataType, Error(error))
                    }
            }
            .ignoreElement()
            .onErrorComplete()
    }

    private fun Synchronizer<*>.checkCancellation(fallback: () -> Unit): Maybe<Boolean> {
        return synchronizationDataTypeProvider.shouldSynchronize(dataType)
            .doOnSuccess { if (!it) fallback.invoke() }
            .filter { it }
    }

    private fun Completable.andThenSynchronizeWith(
        vararg synchronizers: Synchronizer<*>
    ): Completable {
        val completables = synchronizers
            .map { it.synchronize() }
            .toTypedArray()

        return andThenMerge(*completables)
    }

    private companion object {

        private fun SynchronizationStatus.update(
            dataType: SynchronizationDataType,
            event: SynchronizationEvent
        ): SynchronizationStatus {
            val updatedEvents = events.toMutableMap().apply { this[dataType] = event }
            return copy(events = updatedEvents)
        }

    }

}
