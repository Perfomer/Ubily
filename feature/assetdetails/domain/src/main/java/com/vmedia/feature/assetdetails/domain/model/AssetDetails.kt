package com.vmedia.feature.assetdetails.domain.model

data class AssetDetails(
    val asset: DetailedAsset = DetailedAsset(),
    val publisher: PublisherModel = PublisherModel(),
    val reviews: ReviewsModel = ReviewsModel()
)