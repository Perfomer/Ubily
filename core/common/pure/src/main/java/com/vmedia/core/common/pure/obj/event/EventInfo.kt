package com.vmedia.core.common.pure.obj.event

import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.common.pure.obj.Money
import com.vmedia.core.common.pure.util.EMPTY_DATE
import java.util.*

sealed class EventInfo<Content>(
    open val id: Long,
    open val date: Date,
    open val content: Content,
    open val type: EventType
) {

    object StubEventInfo: EventInfo<Int>(0, EMPTY_DATE, 0, EventType.INITIALIZATION)

    sealed class EventListInfo<Content>(
        override val id: Long,
        override val date: Date,
        override val content: List<Content>,
        override val type: EventType
    ) : EventInfo<List<Content>>(id, date, content, type) {

        data class EventSale(
            override val id: Long,
            override val date: Date = EMPTY_DATE,
            override val content: List<SaleInfo> = emptyList()
        ) : EventListInfo<SaleInfo>(id, date, content, EventType.SALE)

        data class EventFreeDownload(
            override val id: Long,
            override val date: Date = EMPTY_DATE,
            override val content: List<SaleInfo> = emptyList()
        ) : EventListInfo<SaleInfo>(id, date, content, EventType.FREE_DOWNLOAD)

        data class EventAsset(
            override val id: Long,
            override val date: Date = EMPTY_DATE,
            override val content: List<AssetInfo> = emptyList()
        ) : EventListInfo<AssetInfo>(id, date, content, EventType.ASSET)

    }

    data class EventReview(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: ReviewInfo
    ) : EventInfo<ReviewInfo>(id, date, content, EventType.REVIEW)

    data class EventPayout(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: PayoutInfo
    ) : EventInfo<PayoutInfo>(id, date, content, EventType.PAYOUT)

    data class EventRevenue(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: RevenueInfo
    ) : EventInfo<RevenueInfo>(id, date, content, EventType.REVENUE)

    data class EventAnniversarySale(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: SaleInfo
    ) : EventInfo<SaleInfo>(id, date, content, EventType.ANNIVERSARY_SALE)

    data class EventInitialization(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: Money
    ) : EventInfo<Money>(id, date, content, EventType.INITIALIZATION)

}