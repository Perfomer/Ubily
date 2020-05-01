package com.vmedia.feature.assetdetails.domain.model

import android.os.Parcelable
import androidx.annotation.IntRange
import com.vmedia.core.common.pure.obj.Rating
import com.vmedia.core.data.internal.database.model.ReviewDetailed
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewsModel(
    val reviews: List<ReviewDetailed> = emptyList(),
    val reviewsCount: Int = reviews.size,
    val averageReviewsRating: Double = 0.0,
    val oneStarStats: StarStats = StarStats(),
    val twoStarsStats: StarStats = StarStats(),
    val threeStarsStats: StarStats = StarStats(),
    val fourStarsStats: StarStats = StarStats(),
    val fiveStarsStats: StarStats = StarStats()
) : Parcelable {

    internal companion object {

        internal fun from(reviews: List<ReviewDetailed>): ReviewsModel {
            fun createStarStats(@Rating starCount: Int): StarStats {
                val ratingReviewsCount = reviews.count { it.rating == starCount }
                return StarStats.from(ratingReviewsCount, reviews.size)
            }

            val averageRating =
                if (reviews.isEmpty()) 0.0
                else reviews.sumBy(ReviewDetailed::rating) / reviews.size.toDouble()

            return ReviewsModel(
                reviews = reviews,
                averageReviewsRating = averageRating,
                oneStarStats = createStarStats(1),
                twoStarsStats = createStarStats(2),
                threeStarsStats = createStarStats(3),
                fourStarsStats = createStarStats(4),
                fiveStarsStats = createStarStats(5)
            )
        }

    }

}

@Parcelize
data class StarStats(
    val count: Int = 0,
    @IntRange(from = 0, to = 100) val percent: Int = 0
) : Parcelable {

    internal companion object {

        internal fun from(
            ratingReviewsCount: Int,
            allReviewsCount: Int
        ): StarStats {
            val percent =
                if (allReviewsCount == 0) 0
                else ratingReviewsCount * 100 / allReviewsCount

            return StarStats(
                count = ratingReviewsCount,
                percent = percent
            )
        }

    }

}