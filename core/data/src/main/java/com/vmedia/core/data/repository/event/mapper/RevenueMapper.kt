package com.vmedia.core.data.repository.event.mapper

import androidx.annotation.WorkerThread
import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.common.pure.obj.event.EventInfo.EventRevenue
import com.vmedia.core.common.pure.obj.event.RevenueInfo
import com.vmedia.core.common.pure.util.ObservableMapper
import com.vmedia.core.common.pure.util.rx.zipWith
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Revenue
import io.reactivex.Observable
import io.reactivex.Single

internal class RevenueMapper(
    private val source: DatabaseDataSource
) : ObservableMapper<Event, EventRevenue> {

    override fun map(from: Event): Observable<EventRevenue> {
        return source.getEventRevenue(from.id)
            .zipWith { source.getPeriod(it.periodId) }
            .flatMap { (revenue, period) ->
                createEventRevenueWithPreviousRevenue(from, period, revenue)
            }
            .toObservable()
    }

    private fun createEventRevenueWithPreviousRevenue(
        event: Event,
        eventPeriod: Period,
        revenue: Revenue
    ): Single<EventRevenue> {
        val previousRevenueId = revenue.periodId - 1

        return source.getRevenue(previousRevenueId)
            .onErrorReturnItem(EMPTY_REVENUE)
            .map { createEventRevenue(event, eventPeriod, revenue, it) }
    }

    @WorkerThread
    private fun createEventRevenue(
        event: Event,
        eventPeriod: Period,
        revenue: Revenue,
        previousPeriodRevenue: Revenue
    ): EventRevenue {
        return EventRevenue(
            id = event.id,
            date = event.date,
            content = createRevenueInfo(revenue, eventPeriod, previousPeriodRevenue)
        )
    }

    @WorkerThread
    private fun createRevenueInfo(
        revenue: Revenue,
        eventPeriod: Period,
        previousRevenue: Revenue
    ): RevenueInfo {
        return RevenueInfo(
            period = eventPeriod,
            amount = revenue.valueUsd,
            sale = revenue.isSale,
            revenueDelta = calculateRevenuesDifference(revenue, previousRevenue)
        )
    }

    @WorkerThread
    private fun calculateRevenuesDifference(
        currentRevenue: Revenue,
        previousRevenue: Revenue
    ): Double {
        val currentPeriodAmount = currentRevenue.valueUsd.toDouble()
        val previousPeriodAmount = previousRevenue.valueUsd.toDouble()

        return when {
            currentPeriodAmount == previousPeriodAmount -> {
                0.0
            }

            currentPeriodAmount > 0 && previousPeriodAmount > 0 -> {
                100 / (previousPeriodAmount / (currentPeriodAmount - previousPeriodAmount))
            }

            previousPeriodAmount == 0.0 -> {
                100.0
            }

            else -> {
                0.0
            }
        }
    }

    private companion object {

        private val EMPTY_REVENUE = Revenue(0L)

    }

}
