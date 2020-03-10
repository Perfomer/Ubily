package com.vmedia.core.sync

import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.andThenMerge
import com.vmedia.core.sync.SynchronizationEvent.Loading
import com.vmedia.core.sync.SynchronizationEvent.PeriodsReceived
import com.vmedia.core.sync.SynchronizationEventType.PERIODS_RECEIVED
import com.vmedia.core.sync.cache.CachedDatabaseDataSourceDecorator
import com.vmedia.core.sync.cache.CachedNetworkDataSourceDecorator
import com.vmedia.core.sync.synchronizer.PublisherCredentialsSynchronizer
import com.vmedia.core.sync.synchronizer.SynchronizationPeriodsProvider
import com.vmedia.core.sync.synchronizer.Synchronizer
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

internal class SynchronizationDataSourceImpl(
    private val networkDataSource: CachedNetworkDataSourceDecorator,
    private val databaseDataSource: CachedDatabaseDataSourceDecorator,

    private val credentialsSynchronizer: PublisherCredentialsSynchronizer,

    private val publisherSynchronizer: _PublisherSynchronizer,
    private val assetSynchronizer: _AssetSynchronizer,
    private val reviewSynchronizer: _ReviewSynchronizer,
    private val saleSynchronizer: _SaleSynchronizer,
    private val downloadSynchronizer: _DownloadSynchronizer,
    private val revenueSynchronizer: _RevenueSynchronizer,
    private val payoutSynchronizer: _PayoutSynchronizer,
    private val periodSynchronizer: _PeriodSynchronizer
) : SynchronizationDataSource, SynchronizationPeriodsProvider {

    override val periods: List<Period>
        get() {
            val event = syncStatus.events[PERIODS_RECEIVED] as PeriodsReceived
            return event.items
        }

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
            .doOnTerminate { clear() }
    }

    private fun execute(): Completable {
        val periods by lazy { }

        return credentialsSynchronizer.synchronize()
            .andThenMerge(
                publisherSynchronizer.synchronize(),
                assetSynchronizer.synchronize(),
                periodSynchronizer.synchronize()
            ).andThenMerge(
                reviewSynchronizer.synchronize(),
                saleSynchronizer.synchronize(),
                downloadSynchronizer.synchronize(),
                revenueSynchronizer.synchronize(),
                payoutSynchronizer.synchronize()
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
            .doOnSuccess { syncStatus = syncStatus.update(eventType, it) }
            .ignoreElement()
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