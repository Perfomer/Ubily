package com.vmedia.feature.assetdetails.presentation.recycler

import com.vmedia.core.common.android.view.recycler.base.BaseListItem
import com.vmedia.core.common.pure.obj.ReviewsSortType
import com.vmedia.core.common.pure.util.flatListOf
import com.vmedia.core.data.internal.database.entity.Artwork
import com.vmedia.feature.assetdetails.domain.model.AssetDetails
import com.vmedia.feature.assetdetails.domain.model.DetailedAsset
import com.vmedia.feature.assetdetails.domain.model.PublisherModel
import com.vmedia.feature.assetdetails.domain.model.ReviewsModel
import com.vmedia.feature.assetdetails.presentation.recycler.listitem.ArtworksListItem
import com.vmedia.feature.assetdetails.presentation.recycler.listitem.AssetListItem
import com.vmedia.feature.assetdetails.presentation.recycler.listitem.DescriptionListItem
import com.vmedia.feature.assetdetails.presentation.recycler.listitem.PublisherListItem
import com.vmedia.feature.assetdetails.presentation.recycler.listitem.ReviewsListItem

internal object AssetDetailsListItemFactory {

    fun create(
        assetDetails: AssetDetails,
        reviewsSortType: ReviewsSortType,
        isReviewsExpanded: Boolean,
        isDescriptionExpanded: Boolean,
    ): List<BaseListItem> {
        val asset = assetDetails.asset

        return flatListOf(
            createAssetItem(asset),
            createArtworksItem(asset.artworks),
            createDescriptionItem(asset, isDescriptionExpanded),
            createReviewsItem(assetDetails.reviews, isReviewsExpanded, reviewsSortType),
            createPublisherItem(assetDetails.publisher),
        )
    }

    private fun createAssetItem(asset: DetailedAsset): List<BaseListItem> {
        return listOf(AssetListItem(asset))
    }

    private fun createArtworksItem(artworks: List<Artwork>): List<BaseListItem> {
        return if (artworks.isEmpty()) emptyList() else listOf(ArtworksListItem(artworks))
    }

    private fun createDescriptionItem(
        asset: DetailedAsset,
        isDescriptionExpanded: Boolean,
    ): List<BaseListItem> {
        return listOf(DescriptionListItem(asset, isDescriptionExpanded))
    }

    private fun createReviewsItem(
        reviews: ReviewsModel,
        isReviewsExpanded: Boolean,
        reviewsSortType: ReviewsSortType,
    ): List<BaseListItem> {
        return if (reviews.reviewsCount == 0) emptyList()
        else listOf(
            ReviewsListItem(
                reviews = reviews,
                isReviewsExpanded = isReviewsExpanded,
                reviewsSortType = reviewsSortType,
            )
        )
    }

    private fun createPublisherItem(publisher: PublisherModel): List<BaseListItem> {
        return listOf(PublisherListItem(publisher))
    }
}
