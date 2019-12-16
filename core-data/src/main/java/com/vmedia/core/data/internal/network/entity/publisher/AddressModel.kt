package com.vmedia.core.data.internal.network.entity.publisher

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("lock_address_fields") val lockAddressFields: Boolean,
    @SerializedName("country") val country: String,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("country_name") val countryName: String,
    @SerializedName("lastname") val lastName: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("state") val state: String,
    @SerializedName("address2") val address2: String,
    @SerializedName("email") val email: String,
    @SerializedName("zip") val zip: String,
    @SerializedName("city") val city: String,
    @SerializedName("address") val address: String,
    @SerializedName("vat_no") val vatNo: String,
    @SerializedName("organization") val organization: String
)