package com.vmedia.core.data.internal.network.entity.publisher

import com.google.gson.annotations.SerializedName

data class PublisherDetailsModel(
    @SerializedName("id") val id: Long,
    @SerializedName("activity_url") val activityUrl: String,
    @SerializedName("ga_account") val gaAccount: String,
    @SerializedName("ga_prefix") val gaPrefix: String,
    @SerializedName("business_phone") val businessPhone: String,
    @SerializedName("technical_phone") val technicalPhone: String,
    @SerializedName("business_email") val businessEmail: String,
    @SerializedName("technical_email") val technicalEmail: String,
    @SerializedName("support_email") val supportEmail: String,
    @SerializedName("support_url") val supportUrl: String,
    @SerializedName("short_url") val shortUrl: String,
    @SerializedName("url") val url: String,
    @SerializedName("keyimages") val keyImages: PublisherKeyImagesModel,
    @SerializedName("promo_video_link") val promoVideoLink: String,
    @SerializedName("promo_headline") val promoHeadline: String,
    @SerializedName("about") val about: String,
    @SerializedName("organization_id") val organizationId: Long,
    @SerializedName("technical_name") val technicalName: String,
    @SerializedName("name") val name: String,
    @SerializedName("business_name") val businessName: String,
    @SerializedName("description") val description: String
)