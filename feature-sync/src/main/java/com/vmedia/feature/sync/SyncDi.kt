package com.vmedia.feature.sync

import androidx.fragment.app.Fragment
import com.vmedia.core.common.util.Mapper
import com.vmedia.core.sync.SynchronizationStatus
import com.vmedia.feature.sync.domain.SyncInteractor
import com.vmedia.feature.sync.presentation.SyncFragment
import com.vmedia.feature.sync.presentation.SyncScreenMode
import com.vmedia.feature.sync.presentation.mapper.StatusMapper
import com.vmedia.feature.sync.presentation.model.SyncStatus
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal typealias _StatusMapper = Mapper<SynchronizationStatus, SyncStatus>

const val BEAN_FRAGMENT_SYNC = "SyncFragment"

val syncModule = module {
    single { SyncInteractor(get()) }
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