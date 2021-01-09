package com.vmedia.feature.assetdetails.presentation

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.view.isVisible
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.vmedia.core.common.android.mvi.MviFragment
import com.vmedia.core.common.android.util.*
import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.android.view.system.SystemUiColorMode
import com.vmedia.core.common.pure.obj.ReviewsSortType
import com.vmedia.core.common.pure.util.cropToString
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.core.data.internal.database.entity.MediaType
import com.vmedia.feature.assetdetails.api.AssetDetailsNavigator
import com.vmedia.feature.assetdetails.domain.model.DetailedAsset
import com.vmedia.feature.assetdetails.domain.model.KeywordModel
import com.vmedia.feature.assetdetails.domain.model.PublisherModel
import com.vmedia.feature.assetdetails.domain.model.ReviewsModel
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent.*
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsState
import com.vmedia.feature.assetdetails.presentation.recycler.keyword.KeywordsAdapter
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.delegate.artworksListAdapterDelegate
import com.vmedia.feature.assetdetails.presentation.recycler.newadapter.delegate.descriptionAdapterDelegate
import com.vmedia.feature.assetdetails.presentation.recycler.review.ReviewsAdapter
import kotlinx.android.synthetic.main.assetdetails_card_asset.*
import kotlinx.android.synthetic.main.assetdetails_card_description.*
import kotlinx.android.synthetic.main.assetdetails_card_publisher.*
import kotlinx.android.synthetic.main.assetdetails_card_reviews.*
import kotlinx.android.synthetic.main.assetdetails_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

internal class AssetDetailsFragment : MviFragment<AssetDetailsIntent, AssetDetailsState, Nothing>(
    layoutResource = R.layout.assetdetails_fragment,
    initialIntent = LoadData
) {

    private val navigator: AssetDetailsNavigator
        get() = activity as AssetDetailsNavigator

    private val reviewsAdapter by lazy { ReviewsAdapter(navigator::navigateToUser) }

    private val adapter: ListDelegationAdapter<List<BaseListItem>> by lazy {
        ListDelegationAdapter(
            artworksListAdapterDelegate(
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
            )
        )
    }

    private var errorSnackbar: Snackbar? = null

    private var assetId: Long by argument()

    override fun provideViewModel() = getViewModel<AssetDetailsViewModel> { parametersOf(assetId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assetdetails_root.addSystemBottomPadding()
        assetdetails_toolbar.addSystemTopPadding()

        assetdetails_reviews_scrim.setOnClickListener { postIntent(ExpandReviews) }
        assetdetails_reviews_viewmore.setOnClickListener { postIntent(ExpandReviews) }

        assetdetails_back.setOnClickListener(::goBack)
        assetdetails_publisher.setOnClickListener(navigator::navigateToPublisher)

        assetdetails_icon.setOnClickListener {
            currentState!!.payload.asset.iconImage?.let(navigator::navigateToGallery)
        }

        assetdetails_externallink.setOnClickListener {
            navigator.navigateToUrl(currentState!!.payload.asset.shortUrl!!)
        }

        assetdetails_reviews_sort_value.setOnItemSelectedListener {
            postIntent(UpdateSortType(ReviewsSortType.values()[it]))
        }

        assetdetails_reviews_1.disableTouches()
        assetdetails_reviews_2.disableTouches()
        assetdetails_reviews_3.disableTouches()
        assetdetails_reviews_4.disableTouches()
        assetdetails_reviews_5.disableTouches()

        assetdetails_reviews_sort_value.adapter = ArrayAdapters.createFromResources(
            context!!,
            reviewsSortTypeLabelResources,
            R.layout.common_reviews_sort_item
        )

        assetdetails_content.adapter = adapter
        assetdetails_reviews_list.adapter = reviewsAdapter
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
        adapter.notifyDataSetChanged()

        with(state.payload) {
            renderAsset(asset)
            renderReviews(reviews, state.reviewsSortType, state.isReviewsExpanded)
            renderPublisher(publisher)
        }
    }

    private fun renderAsset(asset: DetailedAsset) = with(asset) {
        assetdetails_name.diffedValue = name
        assetdetails_identifier.diffedValue = id.toString()
        assetdetails_category.diffedValue = categoryName
        assetdetails_price.diffedValue = "$$priceUsd"
        assetdetails_version.diffedValue = versionName
        assetdetails_status.diffedValue = getString(status.labelResource)
        assetdetails_size.diffedValue = getString(
            R.string.assetdetails_asset_value_size,
            sizeMb.cropToString()
        )

        iconImage?.let { assetdetails_icon.loadCircleImage(it) }
        bigImage?.let { assetdetails_largeimage.loadImage(it) }
    }

    private fun renderPublisher(publisherModel: PublisherModel) = with(publisherModel) {
        assetdetails_publisher_rating.diffedValue = averageRating.cropToString()
        assetdetails_publisher_name.diffedValue = name
        assetdetails_publisher_description.text = description.toSpan()
        assetdetails_publisher_avatar.loadCircleImage(avatar)
    }

    private fun renderReviews(
        reviewsModel: ReviewsModel,
        sortType: ReviewsSortType,
        isExpanded: Boolean,
    ) = with(reviewsModel) {
        val hasReviews = reviewsCount > 0

        assetdetails_reviews.isVisible = hasReviews

        if (!hasReviews) {
            return
        }

        assetdetails_reviews_sort_value.setSelection(sortType.ordinal)
        assetdetails_reviews_count.text = getString(R.string.reviews_count, reviewsCount).toSpan()

        assetdetails_reviews_rating_value.diffedValue = averageReviewsRating.cropToString()

        assetdetails_reviews_1_count.diffedValue = oneStarStats.count.toString()
        assetdetails_reviews_2_count.diffedValue = twoStarsStats.count.toString()
        assetdetails_reviews_3_count.diffedValue = threeStarsStats.count.toString()
        assetdetails_reviews_4_count.diffedValue = fourStarsStats.count.toString()
        assetdetails_reviews_5_count.diffedValue = fiveStarsStats.count.toString()

        assetdetails_reviews_1_progress.diffedValue = oneStarStats.percent
        assetdetails_reviews_2_progress.diffedValue = twoStarsStats.percent
        assetdetails_reviews_3_progress.diffedValue = threeStarsStats.percent
        assetdetails_reviews_4_progress.diffedValue = fourStarsStats.percent
        assetdetails_reviews_5_progress.diffedValue = fiveStarsStats.percent

        assetdetails_reviews_viewmore.isVisible = !isExpanded
        assetdetails_reviews_scrim.isVisible = !isExpanded

        reviewsAdapter.setItems(reviews, isExpanded)
    }

    internal companion object {

        private const val MAX_COLLAPSED_DESCRIPTION_LINES = 10

        private val List<Artwork>.images: List<String>
            get() = this.filter { it.mediaType == MediaType.IMAGE }
                .map(Artwork::previewUrl)

        internal fun newInstance(assetId: Long) = AssetDetailsFragment().apply {
            this.assetId = assetId
        }
    }
}
