package com.vmedia.core.data.obj

import android.os.Parcelable
import com.vmedia.core.common.obj.Money
import com.vmedia.core.data.internal.database.entity.EventType
import kotlinx.android.parcel.Parcelize
import java.util.*

sealed class EventInfo(
    open val id: Long,
    open val date: Date,
    val type: EventType
): Parcelable {

    @Parcelize
    data class EventSale(
        override val id: Long,
        override val date: Date,
        val sales: List<SaleInfo>
    ): EventInfo(id, date, EventType.SALE)

    @Parcelize
    data class EventFreeDownload(
        override val id: Long,
        override val date: Date,
        val downloads: List<SaleInfo>
    ): EventInfo(id, date, EventType.FREE_DOWNLOAD)

    @Parcelize
    data class EventReview(
        override val id: Long,
        override val date: Date,
        val review: ReviewInfo
    ): EventInfo(id, date, EventType.REVIEW)

    @Parcelize
    data class EventAsset(
        override val id: Long,
        override val date: Date,
        val assets: List<AssetInfo>
    ): EventInfo(id, date, EventType.ASSET)

    @Parcelize
    data class EventPayout(
        override val id: Long,
        override val date: Date,
        val payout: PayoutInfo
    ): EventInfo(id, date, EventType.PAYOUT)

    @Parcelize
    data class EventRevenue(
        override val id: Long,
        override val date: Date,
        val revenue: RevenueInfo
    ): EventInfo(id, date, EventType.REVENUE)

    @Parcelize
    data class EventAnniversarySale(
        override val id: Long,
        override val date: Date,
        val sale: SaleInfo
    ): EventInfo(id, date, EventType.ANNIVERSARY_SALE)

    @Parcelize
    data class EventInitialization(
        override val id: Long,
        override val date: Date,
        val salesAmount: Money
    ): EventInfo(id, date, EventType.INITIALIZATION)

}