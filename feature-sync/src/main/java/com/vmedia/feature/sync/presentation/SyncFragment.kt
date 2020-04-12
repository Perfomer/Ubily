package com.vmedia.feature.sync.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.vmedia.core.common.mvi.MviFragment
import com.vmedia.core.common.util.argument
import com.vmedia.core.common.util.init
import com.vmedia.core.common.util.toSpan
import com.vmedia.core.navigation.navigator.sync.SyncNavigator
import com.vmedia.core.navigation.navigator.sync.SyncScreenMode
import com.vmedia.core.navigation.navigator.sync.SyncScreenMode.INITIAL
import com.vmedia.core.navigation.navigator.sync.SyncScreenMode.REGULAR
import com.vmedia.feature.sync.R
import com.vmedia.feature.sync.SyncViewModel
import com.vmedia.feature.sync.presentation.mvi.SyncIntent
import com.vmedia.feature.sync.presentation.mvi.SyncIntent.ObserveSyncStatus
import com.vmedia.feature.sync.presentation.mvi.SyncIntent.StartSync
import com.vmedia.feature.sync.presentation.mvi.SyncState
import com.vmedia.feature.sync.presentation.mvi.SyncSubscription
import com.vmedia.feature.sync.presentation.recycler.SyncAdapter
import kotlinx.android.synthetic.main.sync_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

internal class SyncFragment : MviFragment<SyncIntent, SyncState, SyncSubscription>(
    layoutResource = R.layout.sync_fragment,
    initialIntent = ObserveSyncStatus
) {

    private val adapter by lazy { SyncAdapter() }

    private val navigator: SyncNavigator
        get() = activity as SyncNavigator

    private var mode: SyncScreenMode by argument()


    override fun provideViewModel() = getViewModel<SyncViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sync_hint_longtime.text = getString(R.string.sync_hint_longtime).toSpan()
        sync_data.init(adapter)

        sync_action_start.setOnClickListener { postIntent(StartSync) }
        sync_action_retry.setOnClickListener { postIntent(StartSync) }
//        sync_action_cancel.setOnClickListener { postIntent(CancelSync) }
    }

    override fun onStart() {
        super.onStart()

        if (mode == INITIAL) {
            postIntent(StartSync)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sync_data.adapter = null
    }

    override fun render(state: SyncState) {
        val status = state.status

        adapter.items = status.dataStatuses

        sync_action_start.isVisible = !state.inProgress && mode == REGULAR
//        sync_action_cancel.isVisible = state.inProgress && mode == REGULAR
        sync_action_retry.isVisible = status.hasErrors
    }

    override fun onSubscriptionReceived(subscription: SyncSubscription) {
        when (subscription) {
            SyncSubscription.SyncFinished -> {
                if (mode == INITIAL) {
                    navigator.onSynchronizationSucceed()
                }
            }
        }
    }

    internal companion object {

        fun newInstance(mode: SyncScreenMode) = SyncFragment().apply {
            this.mode = mode
        }

    }

}