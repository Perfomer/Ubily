package com.vmedia.feature.assetdetails.presentation

import com.vmedia.core.common.mvi.MviViewModel
import com.vmedia.feature.assetdetails.domain.AssetDetailsInteractor
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsAction
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsAction.*
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.ExpandDescription
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.LoadData
import com.vmedia.feature.splash.presentation.mvi.AssetDetailsState

internal class AssetDetailsViewModel(
    private val assetId: Long,
    private val interactor: AssetDetailsInteractor
) : MviViewModel<AssetDetailsIntent, AssetDetailsAction, AssetDetailsState, Nothing>(
    initialState = AssetDetailsState()
) {

    override fun act(
        state: AssetDetailsState,
        intent: AssetDetailsIntent
    ) = when (intent) {
        LoadData -> interactor.getAsset(assetId)
            .asFlowSource(LoadData::class)
            .map<AssetDetailsAction>(::AssetLoadingSucceed)
            .startWith(AssetLoadingStarted)
            .onErrorReturn(::AssetLoadingFailed)

        ExpandDescription -> TODO()
    }

    override fun reduce(
        oldState: AssetDetailsState,
        action: AssetDetailsAction
    ) = when (action) {
        AssetLoadingStarted -> oldState.copy(isLoading = true, error = null)
        is AssetLoadingSucceed -> oldState.copy(isLoading = false, payload = action.payload)
        is AssetLoadingFailed -> oldState.copy(isLoading = false, error = action.error)
    }

}