package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.util.each
import com.vmedia.core.data.internal.database.entity.EventType
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.sync.event.EventModel
import org.junit.Test

class ReviewEventExtractorTest {

    private val extractor = ReviewEventExtractor(::getReviewId)

    @Test
    fun extract_zero() {
        extractor.extract(emptyList())
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.isEmpty() }
            .dispose()
    }

    @Test
    fun extract_one() {
        extractor.extract(MOCKY_REVIEWS.take(1))
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.size == 1 }
            .assertValue { it[0].type == EventType.REVIEW }
            .assertValue { it[0].entities == MOCKY_IDS.take(1) }
    }

    @Test
    fun extract_many() {
        extractor.extract(MOCKY_REVIEWS)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.size == MOCKY_REVIEWS.size }
            .assertValue { it.each { it.type == EventType.REVIEW } }
            .assertValue { it.map(EventModel::entities).flatten() == MOCKY_IDS }
    }

    private companion object {

        private val MOCKY_REVIEWS = listOf(
            createReview(1),
            createReview(2),
            createReview(3),
            createReview(4),
            createReview(5)
        )

        private val MOCKY_IDS = MOCKY_REVIEWS.map { it.id }

        private fun getReviewId(authorId: Long, assetId: Long): Long {
            return MOCKY_REVIEWS.find {
                it.authorId == authorId
                        && it.assetId == assetId
            }!!.id
        }

        private fun createReview(id: Long): Review {
            return Review(
                id = id,
                assetId = id,
                authorId = id
            )
        }

    }
}