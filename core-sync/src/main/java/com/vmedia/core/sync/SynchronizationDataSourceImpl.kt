package com.vmedia.core.sync

import com.vmedia.core.common.util.andThenMerge
import com.vmedia.core.sync.SynchronizationEvent.*
import com.vmedia.core.sync.SynchronizationEventType.PERIODS_RECEIVED
import com.vmedia.core.sync.cache.CachedDatabaseDataSourceDecorator
import com.vmedia.core.sync.cache.CachedNetworkDataSourceDecorator
import com.vmedia.core.sync.synchronizer.MutableSynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.PublisherCredentialsSynchronizer
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

internal class SynchronizationDataSourceImpl(
    private val networkDataSource: CachedNetworkDataSourceDecorator,
    private val databaseDataSource: CachedDatabaseDataSourceDecorator,
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
                Throwable("There's already running synchronization. You can't run the second one until the first one is finished.")
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
                    .doOnComplete {
                        val event = syncStatus.events[PERIODS_RECEIVED] as PeriodsReceived
                        periodsProvider.periods = event.items
                    }
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

    private fun <T : SynchronizationEvent> Synchronizer<T>.synchronize(): Completable {
        return execute()
            .doOnSubscribe { syncStatus = syncStatus.update(eventType, Loading) }
            .doOnError { syncStatus = syncStatus.update(eventType, Error(it)) }
            .doOnSuccess { syncStatus = syncStatus.update(eventType, it) }
            .ignoreElement()
            .onErrorComplete()
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
            eventType: SynchronizationEventType,
            event: SynchronizationEvent
        ): SynchronizationStatus {
            val updatedEvents = events.toMutableMap().apply { this[eventType] = event }
            return copy(events = updatedEvents)
        }

    }

}