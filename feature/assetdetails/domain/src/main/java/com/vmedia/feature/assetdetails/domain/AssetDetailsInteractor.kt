package com.vmedia.feature.assetdetails.domain

import com.vmedia.core.common.pure.util.rx.mapItems
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Keyword
import com.vmedia.feature.assetdetails.domain.model.*
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

class AssetDetailsInteractor(
    private val source: DatabaseDataSource
) {

    fun getAsset(id: Long): Observable<AssetDetails> {
        return source.getAssetObservable(id)
            .flatMap {
                Observables.zip(
                    getDetailedAsset(it),
                    getPublisher(),
                    getReviews(id)
                ) { detailedAsset, publisher, reviews ->
                    AssetDetails(
                        asset = detailedAsset,
                        publisher = publisher,
                        reviews = reviews
                    )
                }
            }
    }

    private fun getDetailedAsset(asset: Asset): Observable<DetailedAsset> {
        return Observables.zip(
            source.getCategory(asset.categoryId).toObservable(),
            source.getArtworks(asset.id),
            getKeywords(asset.id)
        ) { category, artworks, keywords ->
            DetailedAsset(
                id = asset.id,
                categoryName = category.name,
                name = asset.name,
                versionName = asset.versionName,
                shortUrl = asset.shortUrl,
                averageRating = asset.averageRating,
                priceUsd = asset.priceUsd,
                status = asset.status,
                sizeMb = asset.totalFileSize,
                description = asset.description,
                bigImage = asset.bigImage,
                iconImage = asset.iconImage,
                artworks = artworks,
                keywords = keywords
            )
        }
    }

    private fun getPublisher(): Observable<PublisherModel> {
        return Observables.zip(
            source.getPublisherObservable(),
            source.getAverageAssetsRating()
        ) { publisher, averageRating ->
            PublisherModel(
                id = publisher.id,
                name = publisher.name,
                avatar = publisher.smallImageUrl,
                description = publisher.description,
                averageRating = averageRating
            )
        }
    }

    private fun getReviews(assetId: Long): Observable<ReviewsModel> {
        return source.getDetailedReviews(assetId)
            .map(ReviewsModel.Companion::from)
    }

    private fun getKeywords(assetId: Long): Observable<List<KeywordModel>> {
        return source.getKeywords(assetId)
            .mapItems(Keyword::toModel)
    }

}
