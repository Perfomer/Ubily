package com.vmedia.feature.assetlist.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vmedia.core.common.mvi.MviFragment
import com.vmedia.core.common.util.*
import com.vmedia.core.domain.model.AssetShortInfo
import com.vmedia.core.navigation.navigator.assetlist.AssetListNavigator
import com.vmedia.feature.assetlist.presentation.mvi.AssetListIntent
import com.vmedia.feature.assetlist.presentation.mvi.AssetListIntent.LoadData
import com.vmedia.feature.assetlist.presentation.mvi.AssetListState
import com.vmedia.feature.assetlist.presentation.recycler.AssetListAdapter
import kotlinx.android.synthetic.main.assetlist_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

internal class AssetListFragment : MviFragment<AssetListIntent, AssetListState, Nothing>(
    layoutResource = R.layout.assetlist_fragment,
    initialIntent = LoadData
) {

    private val navigator: AssetListNavigator
        get() = activity as AssetListNavigator

    private val adapter = AssetListAdapter(::onAssetClick)

    override fun provideViewModel() = getViewModel<AssetListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assetlist_toolbar_search_wrap.addSystemTopPadding()
        assetlist_list.addSystemVerticalPadding()

        assetlist_toolbar_back.setOnClickListener(::goBack)
        assetlist_toolbar_account.setOnClickListener(navigator::navigateToPublisher)
        assetlist_toolbar_search.setOnClickListener(navigator::navigateToAssetSearch)
        assetlist_toolbar_filter.setOnClickListener(navigator::navigateToAssetSearch)

        assetlist_list.init(
            adapter = adapter,
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        assetlist_list.adapter = null
    }

    override fun render(state: AssetListState) {
        assetlist_loading.isVisible = state.isLoading && state.payload.isEmpty()
        assetlist_toolbar_account.loadCircleImage(state.publisherAvatar)
        adapter.items = state.payload
    }


    private fun onAssetClick(asset: AssetShortInfo) {
        navigator.navigateToAsset(asset.id)
    }

}