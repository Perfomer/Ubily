package com.vmedia.core.data.repository.event.mapper

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.Month
import com.vmedia.core.common.obj.of
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Revenue
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
internal class RevenueMapperTest {

    private val source: DatabaseDataSource = mock {
        on { getPeriod(any()) } doReturn Single.just(MOCKY_PERIOD)
        on { getRevenue(any()) } doReturn Single.just(MOCKY_REVENUE_PREVIOUS)
    }

    private val mapper: RevenueMapper
        get() = RevenueMapper(source)

    @Test
    fun map_empty() {
        // Given
        val error = Throwable()
        whenever(source.getEventRevenue(any())).thenReturn(Single.error(error))

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
        whenever(source.getEventRevenue(any())).thenReturn(Single.just(MOCKY_REVENUE_CURRENT))

        // When
        val mapping = mapper.map(MOCKY_EVENT)

        // Then
        mapping.test()
            .assertComplete()
            .assertValue { it.id == MOCKY_EVENT.id }
            .assertValue { it.date == MOCKY_EVENT.date }
            .assertValue { it.content.amount == MOCKY_REVENUE_CURRENT.valueUsd }
            .assertValue { it.content.revenueDelta == 20.0 }
            .dispose()
    }

    private companion object {

        private val MOCKY_PERIOD = Month.APRIL of 2020

        private val MOCKY_EVENT = Event(type = EventType.REVENUE)

        private val MOCKY_REVENUE_CURRENT = Revenue(
            periodId = 2L,
            valueUsd = BigDecimal(120)
        )

        private val MOCKY_REVENUE_PREVIOUS = Revenue(
            periodId = 1L,
            valueUsd = BigDecimal(100)
        )

    }

}