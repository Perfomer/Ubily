package com.vmedia.core.data.internal.network.entity.publisher

import com.google.gson.annotations.SerializedName

data class PublisherModel (
    @SerializedName("downloader") val downloader : Boolean,
    @SerializedName("language_code") val languageCode : String,
    @SerializedName("currency") val currency : String,
    @SerializedName("email") val email : String,
    @SerializedName("v2editor_allowed") val updatedEditorAllowed : Boolean,
    @SerializedName("has_accepted_latest_terms") val hasAcceptedLatestTerms : Boolean,
    @SerializedName("himself") val himself : Boolean,
    @SerializedName("id") val id : Long,
    @SerializedName("address") val address : Address,
    @SerializedName("publisher") val publisher : PublisherAccountModel,
    @SerializedName("bio") val bio : String,
    @SerializedName("name") val name : String,
    @SerializedName("keyimage") val keyImages : PublisherKeyImagesFullModel,
    @SerializedName("balance") val balance : BalanceModel,
    @SerializedName("editable") val editable : Boolean,
    @SerializedName("v2editor_preferred") val updatedEditorPreferred : Boolean,
    @SerializedName("v2_preferred") val updatedPreferred : Boolean
)