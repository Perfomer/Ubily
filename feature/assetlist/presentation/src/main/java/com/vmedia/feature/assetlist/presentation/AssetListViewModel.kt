package com.vmedia.feature.assetlist.presentation

import com.vmedia.core.common.mvi.MviViewModel
import com.vmedia.feature.assetlist.domain.AssetListInteractor
import com.vmedia.feature.assetlist.presentation.mvi.AssetListAction
import com.vmedia.feature.assetlist.presentation.mvi.AssetListAction.*
import com.vmedia.feature.assetlist.presentation.mvi.AssetListIntent
import com.vmedia.feature.assetlist.presentation.mvi.AssetListIntent.LoadData
import com.vmedia.feature.assetlist.presentation.mvi.AssetListState
import io.reactivex.rxkotlin.Observables

internal class AssetListViewModel(
    private val interactor: AssetListInteractor
) : MviViewModel<AssetListIntent, AssetListAction, AssetListState, Nothing>(
    initialState = AssetListState()
) {

    override fun act(
        state: AssetListState,
        intent: AssetListIntent
    ) = when (intent) {
        LoadData -> {
            Observables.zip(
                interactor.getAssets(),
                interactor.getPublisherAvatar()
            )
                .asFlowSource(LoadData::class)
                .map<AssetListAction> { (assets, avatar) -> AssetsLoadingSucceed(assets, avatar) }
                .startWith(AssetsLoadingStarted)
                .onErrorReturn(::AssetsLoadingFailed)
        }
    }

    override fun reduce(
        oldState: AssetListState,
        action: AssetListAction
    ) = when (action) {
        AssetsLoadingStarted -> oldState.copy(
            isLoading = true,
            error = null
        )

        is AssetsLoadingSucceed -> oldState.copy(
            isLoading = false,
            payload = action.payload,
            publisherAvatar = action.publisherAvatar
        )

        is AssetsLoadingFailed -> oldState.copy(
            isLoading = false,
            error = action.error
        )
    }

}