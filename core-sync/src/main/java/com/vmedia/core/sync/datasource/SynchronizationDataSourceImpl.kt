package com.vmedia.core.sync.datasource

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.andThenMerge
import com.vmedia.core.sync.*
import com.vmedia.core.sync.SynchronizationDataType.PERIODS
import com.vmedia.core.sync.SynchronizationEvent.*
import com.vmedia.core.sync.cache.CachedDatabaseDataSourceDecorator
import com.vmedia.core.sync.cache.CachedNetworkDataSourceDecorator
import com.vmedia.core.sync.synchronizer.MutableSynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.PublisherCredentialsSynchronizer
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

internal class SynchronizationDataSourceImpl(
    private val networkDataSource: CachedNetworkDataSourceDecorator,
    private val databaseDataSource: CachedDatabaseDataSourceDecorator,
    private val synchronizationDataTypeProvider: SynchronizationDataTypeProvider,
    private val periodsProvider: MutableSynchronizationPeriodsProvider,

    private val credentialsSynchronizer: PublisherCredentialsSynchronizer,

    private val publisherSynchronizer: _PublisherSynchronizer,
    private val assetSynchronizer: _AssetSynchronizer,
    private val reviewSynchronizer: _ReviewSynchronizer,
    private val saleSynchronizer: _SaleSynchronizer,
    private val downloadSynchronizer: _DownloadSynchronizer,
    private val revenueSynchronizer: _RevenueSynchronizer,
    private val payoutSynchronizer: _PayoutSynchronizer,
    private val periodSynchronizer: _PeriodSynchronizer,
    private val userSynchronizer: _UserSynchronizer
) : SynchronizationDataSource {

    @Volatile
    override var isSynchronizing = false

    private val statusSubject: Subject<SynchronizationStatus> = BehaviorSubject.create()

    @Volatile
    private var syncStatus = SynchronizationStatus(emptyMap())
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
            .doOnSubscribe { isSynchronizing = true }
            .doOnTerminate { isSynchronizing = false }
            .doOnTerminate(::clear)
    }

    private fun execute(): Completable {
        return credentialsSynchronizer.synchronize()
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

    private fun clear() {
        networkDataSource.dropCache()
        databaseDataSource.dropCache()

        syncStatus = SynchronizationStatus(emptyMap())
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
                    .doOnError { syncStatus = syncStatus.update(dataType, Error(it)) }
                    .doOnSuccess { syncStatus = syncStatus.update(dataType, Data(it)) }
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