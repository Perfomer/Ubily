package com.vmedia.feature.publisherdetails.domain.model

data class PublisherDetails(
    val id: Long = 0,
    val iconImage: String = "",
    val bigImage: String = "",
    val name: String = "",
    val shortUrl: String = "",
    val description: String = "",
    val averageRating: Double = 0.0
)