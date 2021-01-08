package com.vmedia.feature.sync.presentation

import androidx.work.WorkManager
import com.vmedia.core.common.android.mvi.MviViewModel
import com.vmedia.core.common.pure.util.mapWith
import com.vmedia.core.sync.SynchronizationWorker
import com.vmedia.feature.sync.domain.SyncInteractor
import com.vmedia.feature.sync.presentation.di._StatusMapper
import com.vmedia.feature.sync.presentation.mvi.SyncAction
import com.vmedia.feature.sync.presentation.mvi.SyncAction.SyncStatusUpdated
import com.vmedia.feature.sync.presentation.mvi.SyncIntent
import com.vmedia.feature.sync.presentation.mvi.SyncIntent.ObserveSyncStatus
import com.vmedia.feature.sync.presentation.mvi.SyncIntent.StartSync
import com.vmedia.feature.sync.presentation.mvi.SyncState
import com.vmedia.feature.sync.presentation.mvi.SyncSubscription
import io.reactivex.Completable
import io.reactivex.Observable

internal class SyncViewModel(
    private val interactor: SyncInteractor,
    private val statusMapper: _StatusMapper,
    private val workManager: WorkManager,
) : MviViewModel<SyncIntent, SyncAction, SyncState, SyncSubscription>(
    initialState = SyncState()
) {

    override fun act(
        state: SyncState,
        intent: SyncIntent,
    ) = when (intent) {
        ObserveSyncStatus -> interactor.observeSyncStatus()
            .asFlowSource(ObserveSyncStatus::class)
            .mapWith(statusMapper)
            .map(::SyncStatusUpdated)

        StartSync -> interactor.isSynchronizationSucceedAtLeastOnce()
            .flatMapObservable { isSynchronizationSucceedAtLeastOnce ->
                if (isSynchronizationSucceedAtLeastOnce) {
                    Observable.just<SyncAction>(SyncAction.InitialSyncNotNeeded)
                } else {
                    Completable.fromAction { SynchronizationWorker.startOnceImmediately(workManager) }
                        .onErrorComplete()
                        .andThen(super.act(state, intent))
                }
            }
            .asFlowSource(StartSync::class)
    }

    override fun reduce(
        oldState: SyncState,
        action: SyncAction,
    ) = when (action) {
        is SyncStatusUpdated -> oldState.copy(
            status = action.status,
            inProgress = !action.status.isFinished
        )
        else -> super.reduce(oldState, action)
    }

    override fun publishSubscription(
        state: SyncState,
        action: SyncAction,
    ) = when (action) {
        is SyncStatusUpdated -> {
            if (action.status.isFinished) SyncSubscription.SyncFinished
            else super.publishSubscription(state, action)
        }
        is SyncAction.InitialSyncNotNeeded -> {
            SyncSubscription.SyncFinished
        }
    }

}
