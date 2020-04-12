package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.Month.*
import com.vmedia.core.common.obj.Period
import com.vmedia.core.common.obj.of
import com.vmedia.core.common.obj.startDate
import com.vmedia.core.common.util.each
import com.vmedia.core.data.internal.database.entity.Revenue
import com.vmedia.core.sync.event.EventModel
import org.junit.Test

internal class RevenueEventExtractorTest {

    private val extractor = RevenueEventExtractor

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
        extractor.extract(MOCKY_REVENUES.take(1))
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.size == 1 }
            .assertValue { it[0].type == EventType.REVENUE }
            .assertValue { it[0].entities == MOCKY_IDS.take(1) }
    }

    @Test
    fun extract_many() {
        extractor.extract(MOCKY_REVENUES)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.size == MOCKY_REVENUES.size }
            .assertValue { it.each { it.type == EventType.REVENUE } }
            .assertValue { it.map(EventModel::entities).flatten() == MOCKY_IDS }
    }

    private companion object {

        private val Period.id: Long
            get() = toString().toLong()

        private val MOCKY_PERIODS = listOf(
            DECEMBER of 2019,
            JANUARY of 2020,
            FEBRUARY of 2020,
            MARCH of 2020
        )

        private val MOCKY_IDS = MOCKY_PERIODS.map { it.id }
        private val MOCKY_REVENUES = MOCKY_PERIODS.map {
            Revenue(
                periodId = it.id,
                date = it.startDate
            )
        }

    }

}