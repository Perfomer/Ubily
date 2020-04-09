package com.example.feature.feed.domain.mapper

import com.example.feature.feed.domain.FeedRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.util.sumByBigDecimal
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import com.vmedia.core.data.internal.database.entity.Sale
import com.vmedia.core.data.obj.EventInfo.EventListInfo.EventSale
import com.vmedia.core.data.obj.SaleInfo
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
internal class ProvisionMapperTest {

    private val repository: FeedRepository = mock {
        on { getAsset(any()) } doAnswer {
            val assetId = it.getArgument<Long>(0)
            Single.just(MOCKY_ASSETS[assetId.toInt()])
        }
    }

    private val mapper: ProvisionMapper<EventSale>
        get() = SaleMapper(repository)

    @Test
    fun map_empty() {
        // Given
        whenever(repository.getEventSales(any())).thenReturn(Single.just(emptyList()))

        // When
        val mapping = mapper.map(MOCKY_EVENT)

        // Then
        mapping.test()
            .assertComplete()
            .assertValue { it.id == MOCKY_EVENT.id }
            .assertValue { it.date == MOCKY_EVENT.date }
            .assertValue { it.content.isEmpty() }
            .dispose()
    }

    @Test
    fun map() {
        // Given
        whenever(repository.getEventSales(any())).thenReturn(Single.just(MOCKY_SALES))

        // When
        val mapping = mapper.map(MOCKY_EVENT)

        // Then
        mapping.test()
            .assertComplete()
            .assertValue { it.id == MOCKY_EVENT.id }
            .assertValue { it.date == MOCKY_EVENT.date }
            .assertValue { it.content.size == MOCKY_ASSETS.size }
            .assertValue {
                val expectedSalesAmount = MOCKY_SALES.groupBy { it.assetId }
                    .values
                    .map { it.sumByBigDecimal(Sale::amount) }

                val actualSalesAmount = it.content.map(SaleInfo::summaryPrice)

                actualSalesAmount == expectedSalesAmount
            }
            .dispose()
    }

    private companion object {

        private const val ASSET_COUNT = 10
        private const val SALE_PER_ASSET_COUNT = 3

        private val MOCKY_SALES by lazy {
            val list = mutableListOf<Sale>()
            repeat(ASSET_COUNT) { assetId ->
                repeat(SALE_PER_ASSET_COUNT) { saleId ->
                    list += createSale(saleId.toLong(), assetId.toLong())
                }
            }
            return@lazy list
        }

        private val MOCKY_ASSETS by lazy {
            val list = mutableListOf<Asset>()
            repeat(ASSET_COUNT) {
                list += createAsset(it.toLong())
            }
            return@lazy list
        }

        private val MOCKY_EVENT = Event(type = EventType.SALE)


        private fun createSale(id: Long, assetId: Long): Sale {
            return Sale(
                id = id,
                assetId = assetId,
                priceUsd = BigDecimal(id)
            )
        }

        private fun createAsset(id: Long): Asset {
            return Asset(id = id)
        }

    }

}