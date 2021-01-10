package com.vmedia.feature.assetdetails.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.vmedia.core.common.android.mvi.MviFragment
import com.vmedia.core.common.android.util.addSystemBottomPadding
import com.vmedia.core.common.android.util.addSystemTopPadding
import com.vmedia.core.common.android.util.argument
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.isVisible
import com.vmedia.core.common.android.util.labelResource
import com.vmedia.core.common.android.util.loadCircleImage
import com.vmedia.core.common.android.util.loadImage
import com.vmedia.core.common.android.util.setOnClickListener
import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.android.view.system.SystemUiColorMode
import com.vmedia.core.common.pure.util.cropToString
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.core.data.internal.database.entity.MediaType
import com.vmedia.feature.assetdetails.api.AssetDetailsNavigator
import com.vmedia.feature.assetdetails.domain.model.DetailedAsset
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.ExpandDescription
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.ExpandReviews
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.LoadData
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.UpdateSortType
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsState
import com.vmedia.feature.assetdetails.presentation.recycler.delegate.artworksAdapterDelegate
import com.vmedia.feature.assetdetails.presentation.recycler.delegate.descriptionAdapterDelegate
import com.vmedia.feature.assetdetails.presentation.recycler.delegate.publisherAdapterDelegate
import com.vmedia.feature.assetdetails.presentation.recycler.delegate.reviewsAdapterDelegate
import kotlinx.android.synthetic.main.assetdetails_card_asset.*
import kotlinx.android.synthetic.main.assetdetails_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

internal class AssetDetailsFragment : MviFragment<AssetDetailsIntent, AssetDetailsState, Nothing>(
    layoutResource = R.layout.assetdetails_fragment,
    initialIntent = LoadData
) {

    private val navigator: AssetDetailsNavigator
        get() = activity as AssetDetailsNavigator

    private val adapter: ListDelegationAdapter<List<BaseListItem>> by lazy {
        ListDelegationAdapter(
            artworksAdapterDelegate(
                onArtworkClickListener = { artworkPosition ->
                    navigator.navigateToGallery(
                        images = currentState!!.payload.asset.artworks.images,
                        targetImagesPosition = artworkPosition
                    )
                },
                onShowArtworksClickListener = {
                    navigator.navigateToGallery(currentState!!.payload.asset.artworks.images)
                }
            ),
            descriptionAdapterDelegate(
                onImageClickListener = { currentState!!.payload.asset.bigImage?.let(navigator::navigateToGallery) },
                onCollapsedClickListener = { postIntent(ExpandDescription) },
                onKeywordClickListener = navigator::navigateToAssetsSearch
            ),
            reviewsAdapterDelegate(
                onAuthorClickListener = navigator::navigateToUser,
                onReviewsSortTypeChangedListener = { selectedType -> postIntent(UpdateSortType(selectedType)) },
                onCollapsedReviewsClickListener = { postIntent(ExpandReviews) }
            ),
            publisherAdapterDelegate(
                onPublisherClickListener = navigator::navigateToPublisher
            ),
        )
    }

    private var errorSnackbar: Snackbar? = null

    private var assetId: Long by argument()

    override fun provideViewModel() = getViewModel<AssetDetailsViewModel> { parametersOf(assetId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assetdetails_content.addSystemBottomPadding()
        general_appbar.addSystemTopPadding()

        assetdetails_header.onAssetIconClickListener = { currentState!!.payload.asset.iconImage?.let(navigator::navigateToGallery) }
        assetdetails_header.onExternalLinkClickListener = { navigator.navigateToUrl(currentState!!.payload.asset.shortUrl!!) }

        assetdetails_content.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        systemUiColorMode = SystemUiColorMode.Light
    }

    override fun onStop() {
        super.onStop()
        errorSnackbar = null
    }

    override fun render(state: AssetDetailsState) {
        errorSnackbar?.isVisible = state.error != null
        assetdetails_loading.isVisible = state.isLoading

        adapter.items = state.content
        adapter.notifyDataSetChanged() // todo

        assetdetails_header.asset = state.payload.asset
    }

    internal companion object {

        private val List<Artwork>.images: List<String>
            get() = this.filter { it.mediaType == MediaType.IMAGE }
                .map(Artwork::previewUrl)

        internal fun newInstance(assetId: Long) = AssetDetailsFragment().apply {
            this.assetId = assetId
        }
    }
}
