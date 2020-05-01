package com.vmedia.core.data.repository.event.mapper

import androidx.annotation.WorkerThread
import com.vmedia.core.common.pure.obj.event.EventInfo.EventReview
import com.vmedia.core.common.pure.obj.event.ReviewInfo
import com.vmedia.core.common.pure.util.ObservableMapper
import com.vmedia.core.common.pure.util.zipWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.data.internal.database.entity.User
import io.reactivex.Observable
import io.reactivex.Single

internal class ReviewMapper(
    private val source: DatabaseDataSource
) : ObservableMapper<Event, EventReview> {

    override fun map(from: Event): Observable<EventReview> {
        return source.getEventReview(from.id)
            .zipWith { source.getAsset(it.assetId) }
            .flatMap { (review, asset) -> createEventReviewWithUser(from, review, asset) }
            .toObservable()
    }

    private fun createEventReviewWithUser(
        event: Event,
        review: Review,
        asset: Asset
    ): Single<EventReview> {
        return source.getUser(review.authorId)
            .map { createEventReview(event, review, asset, it) }
    }

    @WorkerThread
    private fun createEventReview(
        event: Event,
        review: Review,
        asset: Asset,
        author: User
    ): EventReview {
        return EventReview(
            id = event.id,
            date = event.date,
            content = createReviewInfo(review, author, asset)
        )
    }

    @WorkerThread
    private fun createReviewInfo(review: Review, author: User, asset: Asset): ReviewInfo {
        return ReviewInfo(
            rating = review.rating,
            authorName = author.name,
            reviewTitle = review.title,
            reviewBody = review.comment.comment,
            publisherReplyBody = review.publisherReply?.comment,
            assetId = asset.id,
            assetIcon = asset.iconImage,
            assetName = asset.name,
            assetAverageRating = asset.averageRating
        )
    }

}