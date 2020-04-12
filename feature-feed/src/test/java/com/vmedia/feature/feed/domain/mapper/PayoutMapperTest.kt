package com.vmedia.feature.feed.domain.mapper

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.Month.APRIL
import com.vmedia.core.common.obj.of
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Payout
import com.vmedia.feature.feed.domain.FeedRepository
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
internal class PayoutMapperTest {

    private val repository: FeedRepository = mock {
        on { getPeriod(any()) } doReturn Single.just(MOCKY_PERIOD)
    }

    private val mapper: PayoutMapper
        get() = PayoutMapper(repository)

    @Test
    fun map_empty() {
        // Given
        val error = Throwable()
        whenever(repository.getEventPayout(any())).thenReturn(Single.error(error))

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
        whenever(repository.getEventPayout(any())).thenReturn(Single.just(MOCKY_PAYOUT))

        // When
        val mapping = mapper.map(MOCKY_EVENT)

        // Then
        mapping.test()
            .assertComplete()
            .assertValue { it.id == MOCKY_EVENT.id }
            .assertValue { it.date == MOCKY_EVENT.date }
            .assertValue { it.content.amount == MOCKY_PAYOUT.valueUsd }
            .dispose()
    }

    private companion object {

        private val MOCKY_PERIOD = APRIL of 2020

        private val MOCKY_EVENT = Event(type = EventType.PAYOUT)

        private val MOCKY_PAYOUT = Payout(
            periodId = 1L,
            valueUsd = BigDecimal(20)
        )

    }

}