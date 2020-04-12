package com.vmedia.feature.feed.domain.mapper

import androidx.annotation.WorkerThread
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.util.ObservableMapper
import com.vmedia.core.common.util.zipWith
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Payout
import com.vmedia.core.data.obj.EventInfo.EventPayout
import com.vmedia.core.data.obj.PayoutInfo
import com.vmedia.feature.feed.domain.FeedRepository
import io.reactivex.Observable

internal class PayoutMapper(
    private val repository: FeedRepository
) : ObservableMapper<Event, EventPayout> {

    override fun map(from: Event): Observable<EventPayout> {
        return repository.getEventPayout(from.id)
            .zipWith { repository.getPeriod(it.periodId) }
            .map { (payout, period) -> createEventPayout(from, payout, period) }
            .toObservable()
    }

    @WorkerThread
    private fun createEventPayout(
        event: Event,
        payout: Payout,
        period: Period
    ): EventPayout {
        return EventPayout(
            id = event.id,
            date = event.date,
            content = createPayoutInfo(payout, period)
        )
    }

    @WorkerThread
    private fun createPayoutInfo(payout: Payout, period: Period): PayoutInfo {
        return PayoutInfo(
            period = period,
            amount = payout.valueUsd,
            auto = payout.autoPayout,
            paypal = payout.paypal,
            failed = payout.isFailed
        )
    }

}