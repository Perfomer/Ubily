package com.vmedia.core.data.obj

import com.vmedia.core.common.obj.Money
import com.vmedia.core.data.internal.database.entity.*
import java.util.*

sealed class EventInfo(
    open val id: Long,
    open val date: Date,
    val type: EventType
) {

    data class EventSale(
        override val id: Long,
        override val date: Date,
        val sales: List<Sale>
    ): EventInfo(id, date, EventType.SALE)

    data class EventFreeDownload(
        override val id: Long,
        override val date: Date,
        val downloads: List<Sale>
    ): EventInfo(id, date, EventType.FREE_DOWNLOAD)

    data class EventComment(
        override val id: Long,
        override val date: Date,
        val comment: Comment
    ): EventInfo(id, date, EventType.COMMENT)

    data class EventAsset(
        override val id: Long,
        override val date: Date,
        val assets: List<Asset>
    ): EventInfo(id, date, EventType.ASSET)

    data class EventPayout(
        override val id: Long,
        override val date: Date,
        val payout: Payout
    ): EventInfo(id, date, EventType.PAYOUT)

    data class EventRevenue(
        override val id: Long,
        override val date: Date,
        val revenue: Revenue
    ): EventInfo(id, date, EventType.REVENUE)

    data class EventAnniversarySale(
        override val id: Long,
        override val date: Date,
        val sale: Sale
    ): EventInfo(id, date, EventType.ANNIVERSARY_SALE)

    data class EventInitialization(
        override val id: Long,
        override val date: Date,
        val salesAmount: Money
    ): EventInfo(id, date, EventType.INITIALIZATION)

}