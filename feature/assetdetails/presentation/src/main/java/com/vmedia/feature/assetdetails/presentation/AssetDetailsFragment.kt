package com.vmedia.feature.assetdetails.presentation

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.vmedia.core.common.mvi.MviFragment
import com.vmedia.core.common.obj.labelResource
import com.vmedia.core.common.util.*
import com.vmedia.core.navigation.navigator.splash.SplashNavigator
import com.vmedia.feature.assetdetails.domain.model.DetailedAsset
import com.vmedia.feature.assetdetails.domain.model.KeywordModel
import com.vmedia.feature.assetdetails.domain.model.PublisherModel
import com.vmedia.feature.assetdetails.domain.model.ReviewsModel
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsIntent
import com.vmedia.feature.assetdetails.presentation.mvi.AssetDetailsState
import kotlinx.android.synthetic.main.assetdetails_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

internal class AssetDetailsFragment : MviFragment<AssetDetailsIntent, AssetDetailsState, Nothing>(
    layoutResource = R.layout.assetdetails_fragment,
    initialIntent = AssetDetailsIntent.LoadData
) {

    private val navigator: SplashNavigator
        get() = activity as SplashNavigator

    private var errorSnackbar: Snackbar? = null

    private var assetId: Long by argument()

    override fun provideViewModel() = getViewModel<AssetDetailsViewModel> {
        parametersOf(assetId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        assetdetails_root.addSystemBottomPadding()

        val function: (v: View) -> Unit = {
            assetdetails_description_viewmore.isVisible = false
            assetdetails_description_scrim.isVisible = false
            assetdetails_description_text.maxLines = Integer.MAX_VALUE
            currentState?.let { renderKeywords(it.payload.asset.keywords) }
        }

        assetdetails_description_scrim.setOnClickListener(function)
        assetdetails_description_viewmore.setOnClickListener(function)

        assetdetails_description_text.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onStop() {
        super.onStop()
        errorSnackbar = null
    }

    override fun render(state: AssetDetailsState) {
        errorSnackbar?.isVisible = state.error != null
        assetdetails_loading.isVisible = state.isLoading

        with(state.payload) {
            renderAsset(asset)
            renderArtworks(asset.artworks)
            renderKeywords(asset.keywords)
            renderPublisher(publisher)
            renderReviews(reviews)
        }
    }

    private fun renderAsset(asset: DetailedAsset) = with(asset) {
        val hasLargeImage = !bigImage.isNullOrBlank()

        assetdetails_name.diffedValue = name
        assetdetails_identifier.diffedValue = id.toString()
        assetdetails_category.diffedValue = categoryName
        assetdetails_price.diffedValue = "$$priceUsd"
        assetdetails_version.diffedValue = versionName
        assetdetails_status.diffedValue = getString(status.labelResource)
        assetdetails_size.diffedValue = String.format("%.2f", sizeMb)

        assetdetails_description_text.text = description.toSpan()

        iconImage?.let { assetdetails_icon.loadCircleImage(it) }
        bigImage?.let { assetdetails_largeimage.loadImage(it) }

        assetdetails_description_image.isVisible = hasLargeImage
        if (hasLargeImage) assetdetails_description_image.loadImage(bigImage)
    }

    private fun renderArtworks(artworks: List<String>) {
        val hasArtworks = artworks.isNotEmpty()

        assetdetails_artworks.isVisible = hasArtworks

        if (!hasArtworks) {
            return
        }
    }

    private fun renderKeywords(keywords: List<KeywordModel>) {
        val hasKeywords = keywords.isNotEmpty()

        assetdetails_description_keywords_list.isVisible = hasKeywords
        assetdetails_description_keywords_title.isVisible = hasKeywords

        if (!hasKeywords) {
            return
        }
    }

    private fun renderPublisher(publisherModel: PublisherModel) = with(publisherModel) {
        assetdetails_publisher_rating.diffedValue = averageRating.cropToString()
        assetdetails_publisher_name.diffedValue = name
        assetdetails_publisher_description.text = description.toSpan()
        assetdetails_publisher_avatar.loadCircleImage(avatar)
    }

    private fun renderReviews(reviewsModel: ReviewsModel) = with(reviewsModel) {
        val hasReviews = reviewsCount > 0

        assetdetails_reviews.isVisible = hasReviews

        if (!hasReviews) {
            return
        }

        assetdetails_reviews_rating_value.diffedValue = averageReviewsRating.cropToString()

        assetdetails_reviews_count.diffedValue = getString(
            R.string.assetdetails_reviews_count,
            reviewsCount
        )

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
    }

    internal companion object {

        fun newInstance(assetId: Long) = AssetDetailsFragment().apply {
            this.assetId = assetId
        }

    }

}