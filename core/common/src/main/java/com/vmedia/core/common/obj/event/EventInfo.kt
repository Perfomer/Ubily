package com.vmedia.core.common.obj.event

import android.os.Parcelable
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.Money
import com.vmedia.core.common.util.EMPTY_DATE
import kotlinx.android.parcel.Parcelize
import java.util.*

sealed class EventInfo<Content>(
    open val id: Long,
    open val date: Date,
    open val content: Content,
    open val type: EventType
) : Parcelable {

    @Parcelize
    object StubEventInfo: EventInfo<Int>(0, EMPTY_DATE, 0, EventType.INITIALIZATION)

    sealed class EventListInfo<Content>(
        override val id: Long,
        override val date: Date,
        override val content: List<Content>,
        override val type: EventType
    ) : EventInfo<List<Content>>(id, date, content, type) {

        @Parcelize
        data class EventSale(
            override val id: Long,
            override val date: Date = EMPTY_DATE,
            override val content: List<SaleInfo> = emptyList()
        ) : EventListInfo<SaleInfo>(id, date, content, EventType.SALE)

        @Parcelize
        data class EventFreeDownload(
            override val id: Long,
            override val date: Date = EMPTY_DATE,
            override val content: List<SaleInfo> = emptyList()
        ) : EventListInfo<SaleInfo>(id, date, content, EventType.FREE_DOWNLOAD)

        @Parcelize
        data class EventAsset(
            override val id: Long,
            override val date: Date = EMPTY_DATE,
            override val content: List<AssetInfo> = emptyList()
        ) : EventListInfo<AssetInfo>(id, date, content, EventType.ASSET)

    }

    @Parcelize
    data class EventReview(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: ReviewInfo
    ) : EventInfo<ReviewInfo>(id, date, content, EventType.REVIEW)

    @Parcelize
    data class EventPayout(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: PayoutInfo
    ) : EventInfo<PayoutInfo>(id, date, content, EventType.PAYOUT)

    @Parcelize
    data class EventRevenue(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: RevenueInfo
    ) : EventInfo<RevenueInfo>(id, date, content, EventType.REVENUE)

    @Parcelize
    data class EventAnniversarySale(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: SaleInfo
    ) : EventInfo<SaleInfo>(id, date, content, EventType.ANNIVERSARY_SALE)

    @Parcelize
    data class EventInitialization(
        override val id: Long,
        override val date: Date = EMPTY_DATE,
        override val content: Money
    ) : EventInfo<Money>(id, date, content, EventType.INITIALIZATION)

}