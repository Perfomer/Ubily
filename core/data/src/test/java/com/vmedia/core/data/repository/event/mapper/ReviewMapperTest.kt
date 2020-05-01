package com.vmedia.core.data.repository.event.mapper

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.data.internal.database.entity.User
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class ReviewMapperTest {

    private val source: DatabaseDataSource = mock {
        on { getAsset(any()) } doReturn Single.just(MOCKY_ASSET)
        on { getUser(any()) } doReturn Single.just(MOCKY_AUTHOR)
    }

    private val mapper: ReviewMapper
        get() = ReviewMapper(source)

    @Test
    fun map_empty() {
        // Given
        val error = Throwable()
        whenever(source.getEventReview(any())).thenReturn(Single.error(error))

        // When
        val mapping = mapper.map(MOCKY_EVENT)

        // Then
        mapping.test()
            .assertError(error)
            .dispose()
    }

    @Test
    fun map() {
        // Given
        whenever(source.getEventReview(any())).thenReturn(Single.just(MOCKY_REVIEW))

        // When
        val mapping = mapper.map(MOCKY_EVENT)

        // Then
        mapping.test()
            .assertComplete()
            .assertValue { it.id == MOCKY_EVENT.id }
            .assertValue { it.date == MOCKY_EVENT.date }
            .assertValue { it.content.reviewBody == MOCKY_REVIEW.title }
            .assertValue { it.content.assetIcon == MOCKY_ASSET.iconImage }
            .assertValue { it.content.authorName == MOCKY_AUTHOR.name }
    }

    private companion object {

        private val MOCKY_EVENT = Event(type = EventType.REVIEW)

        private val MOCKY_AUTHOR = User(name = "username")

        private val MOCKY_ASSET = Asset(
            id = 10,
            iconImage = "image"
        )

        private val MOCKY_REVIEW = Review(
            assetId = MOCKY_ASSET.id,
            authorId = MOCKY_AUTHOR.id
        )

    }

}