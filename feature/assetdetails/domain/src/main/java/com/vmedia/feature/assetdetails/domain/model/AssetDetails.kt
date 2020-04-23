package com.vmedia.feature.assetdetails.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AssetDetails(
    val asset: DetailedAsset = DetailedAsset(),
    val publisher: PublisherModel = PublisherModel(),
    val reviews: ReviewsModel = ReviewsModel()
) : Parcelable