package com.vmedia.core.di

import com.vmedia.feature.assetdetails.domain.di.featureAssetDetailsDomainModule
import com.vmedia.feature.assetdetails.presentation.di.featureAssetDetailsPresentationModule
import com.vmedia.feature.assetlist.domain.di.featureAssetListDomainModule
import com.vmedia.feature.assetlist.presentation.di.featureAssetListPresentationModule
import com.vmedia.feature.auth.data.di.featureAuthDataModule
import com.vmedia.feature.auth.domain.di.featureAuthDomainModule
import com.vmedia.feature.auth.presentation.di.featureAuthPresentationModule
import com.vmedia.feature.eventdetails.domain.di.featureEventDetailsDomainModule
import com.vmedia.feature.eventdetails.presentation.di.featureEventDetailsPresentationModule
import com.vmedia.feature.feed.presentation.di.featureFeedPresentationModule
import com.vmedia.feature.gallery.presentation.di.featureGalleryPresentationModule
import com.vmedia.feature.main.presentation.featureMainPresentationModule
import com.vmedia.feature.publisherdetails.domain.di.featurePublisherDetailsDomainModule
import com.vmedia.feature.publisherdetails.presentation.di.featurePublisherDetailsPresentationModule
import com.vmedia.feature.splash.data.di.featureSplashDataModule
import com.vmedia.feature.splash.domain.di.featureSplashDomainModule
import com.vmedia.feature.splash.presentation.di.featureSplashPresentationModule
import com.vmedia.feature.sync.domain.di.featureSyncDomainModule
import com.vmedia.feature.sync.presentation.di.featureSyncPresentationModule

val featureModules
    get() = listOf(
        featureMainModules,
        featureAuthModules,
        featureSyncModules,
        featureSplashModules,
        featureFeedModules,
        featureEventDetailsModules,
        featurePublisherDetailsModules,
        featureAssetDetailsModules,
        featureAssetListModules,
        featureGalleryModules
    ).flatten()

private val featureAssetDetailsModules = listOf(
    featureAssetDetailsDomainModule, featureAssetDetailsPresentationModule
)

private val featureAssetListModules = listOf(
    featureAssetListDomainModule, featureAssetListPresentationModule
)

private val featureAuthModules = listOf(
    featureAuthDataModule, featureAuthDomainModule, featureAuthPresentationModule
)

private val featureEventDetailsModules = listOf(
    featureEventDetailsDomainModule, featureEventDetailsPresentationModule
)

private val featureSyncModules = listOf(
    featureSyncDomainModule, featureSyncPresentationModule
)

private val featureFeedModules = listOf(
    featureFeedPresentationModule
)

private val featureGalleryModules = listOf(
    featureGalleryPresentationModule
)

private val featureMainModules = listOf(
    featureMainPresentationModule
)

private val featurePublisherDetailsModules = listOf(
    featurePublisherDetailsDomainModule, featurePublisherDetailsPresentationModule
)

private val featureSplashModules = listOf(
    featureSplashDataModule, featureSplashDomainModule, featureSplashPresentationModule
)