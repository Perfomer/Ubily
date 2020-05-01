package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.pure.obj.*
import com.vmedia.core.common.pure.util.each
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.sync.event.EventModel
import org.junit.Test
import java.math.BigDecimal
import java.util.*

internal class ProvisionEventExtractorTest {

    private val extractor = object : ProvisionEventExtractor(EventType.SALE, ::getSaleId) {}

    @Test
    fun extract_zero() {
        extractor.extract(emptyList())
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.isEmpty() }
            .dispose()
    }

    @Test
    fun extract_one() {
        extractor.extract(MOCKY_SALES.take(1))
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.size == 1 }
            .assertValue { it[0].type == EventType.SALE }
            .assertValue { it[0].entities == MOCKY_IDS.take(1) }
    }

    @Test
    fun extract_many() {
        extractor.extract(MOCKY_SALES)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.size == MOCKY_PERIODS.size }
            .assertValue { it.each { it.type == EventType.SALE } }
            .assertValue { it.map(EventModel::entities).flatten() == MOCKY_IDS }
    }

    private companion object {

        private val Period.id: Long
            get() = toString().toLong()

        private val MOCKY_PERIODS = listOf(
            Month.DECEMBER of 2019,
            Month.JANUARY of 2020,
            Month.FEBRUARY of 2020,
            Month.MARCH of 2020
        )

        private val MOCKY_SALES = MOCKY_PERIODS
            .mapIndexed { ind, period ->
                val cnt = 5L
                val result = mutableListOf<Sale>()
                repeat(cnt.toInt()) { result += createSale(period, (ind) * cnt + it) }
                return@mapIndexed result
            }
            .flatten()

        private val MOCKY_IDS = MOCKY_SALES.map(Sale::id)

        private fun getSaleId(date: Date, assetId: Long, priceUsd: BigDecimal): Long {
            return MOCKY_SALES.find {
                it.date == date
                        && it.assetId == assetId
                        && it.priceUsd == priceUsd
            }!!.id
        }

        private fun createSale(period: Period, id: Long): Sale {
            return Sale(
                id = id,
                assetId = id,
                date = period.startDate
            )
        }

    }

}