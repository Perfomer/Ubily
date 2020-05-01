package com.vmedia.feature.sync.presentation.di

import androidx.fragment.app.Fragment
import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.navigation.BEAN_FRAGMENT_SYNC
import com.vmedia.core.navigation.navigator.sync.SyncScreenMode
import com.vmedia.core.sync.SynchronizationStatus
import com.vmedia.feature.sync.presentation.SyncFragment
import com.vmedia.feature.sync.presentation.SyncViewModel
import com.vmedia.feature.sync.presentation.mapper.StatusMapper
import com.vmedia.feature.sync.presentation.model.SyncStatus
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal typealias _StatusMapper = Mapper<SynchronizationStatus, SyncStatus>

val presentationModule = module {
    single { StatusMapper }

    factory<Fragment>(named(BEAN_FRAGMENT_SYNC)) { (mode: SyncScreenMode) ->
        SyncFragment.newInstance(mode)
    }

    viewModel {
        SyncViewModel(
            interactor = get(),
            statusMapper = get<StatusMapper>()
        )
    }
}