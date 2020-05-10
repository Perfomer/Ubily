package com.vmedia.feature.publisherdetails.domain

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.feature.publisherdetails.domain.model.PublisherDetails
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

class PublisherDetailsInteractor(
    private val databaseDataSource: DatabaseDataSource
) {

    fun getPublisher(): Observable<PublisherDetails> {
        return Observables.zip(
            databaseDataSource.getPublisherObservable(),
            databaseDataSource.getAverageAssetsRating()
        ).map { (publisher, rating) ->
            PublisherDetails(
                id = publisher.id,
                name = publisher.name,
                iconImage = publisher.smallImageUrl,
                bigImage = publisher.largeImageUrl,
                averageRating = rating,
                description = publisher.description
            )
        }
    }

}