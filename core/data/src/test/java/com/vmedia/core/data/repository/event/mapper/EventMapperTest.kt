package com.vmedia.core.data.repository.event.mapper

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.EventType.*
import com.vmedia.core.common.obj.Month.APRIL
import com.vmedia.core.common.obj.Month.JANUARY
import com.vmedia.core.common.obj.of
import com.vmedia.core.data.*
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.obj.EventInfo
import com.vmedia.core.data.obj.EventInfo.*
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventSale
import com.vmedia.core.data.obj.PayoutInfo
import com.vmedia.core.data.obj.RevenueInfo
import com.vmedia.core.data.obj.ReviewInfo
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EventMapperTest {

    private val saleMapper: _SaleMapper = mock() {
        on { map(any()) } doReturn Observable.just(EventSale(id = 1))
    }

    private val reviewMapper: _ReviewMapper = mock {
        on { map(any()) } doReturn Observable.just(
            EventReview(id = 2, content = MOCKY_REVIEW)
        )
    }

    private val revenueMapper: _RevenueMapper = mock {
        on { map(any()) } doReturn Observable.just(
            EventRevenue(id = 3, content = MOCKY_REVENUE)
        )
    }

    private val payoutMapper: _PayoutMapper = mock {
        on { map(any()) } doReturn Observable.just(
            EventPayout(id = 4, content = MOCKY_PAYOUT)
        )
    }

    private val downloadMapper: _DownloadMapper = mock()
    private val assetMapper: _AssetMapper = mock()

    private val eventMapper: EventMapper
        get() = EventMapper(
            saleMapper = saleMapper,
            downloadMapper = downloadMapper,
            assetMapper = assetMapper,
            reviewMapper = reviewMapper,
            revenueMapper = revenueMapper,
            payoutMapper = payoutMapper
        )

    @Test
    fun map_empty() {
        // Given
        val items = emptyList<Event>()

        // When
        val mapping = eventMapper.map(items)

        // Then
        mapping.test()
            .assertComplete()
            .assertValue { it.isEmpty() }
            .dispose()
    }

    @Test
    fun map() {
        fun TestObserver<List<EventInfo<*>>>.assertEventsCount(
            eventType: EventType,
            count: Int
        ): TestObserver<List<EventInfo<*>>> {
            return assertValue {
                it.count { event -> event.type == eventType } == count
            }
        }

        // Given
        val items =
            MOCKY_EVENTS

        // When
        val mapping = eventMapper.map(items)

        // Then
        mapping.test()
            .assertComplete()
            .assertValue { it.size == MOCKY_EVENTS.size }
            .assertEventsCount(SALE, 2)
            .assertEventsCount(REVIEW, 1)
            .assertEventsCount(REVENUE, 1)
            .assertEventsCount(PAYOUT, 1)
            .dispose()
    }

    private companion object {

        private val MOCKY_EVENTS = listOf(
            Event(id = 1, type = SALE),
            Event(id = 2, type = REVIEW),
            Event(id = 3, type = SALE),
            Event(id = 4, type = PAYOUT),
            Event(id = 5, type = REVENUE)
        )

        private val MOCKY_REVIEW = ReviewInfo(assetId = 10)
        private val MOCKY_PAYOUT = PayoutInfo(period = APRIL of 2020)
        private val MOCKY_REVENUE = RevenueInfo(period = JANUARY of 2020)

    }

}