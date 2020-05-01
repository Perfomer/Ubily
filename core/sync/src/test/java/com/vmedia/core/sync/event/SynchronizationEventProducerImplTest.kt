package com.vmedia.core.sync.event

import com.nhaarman.mockitokotlin2.*
import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.common.pure.obj.EventType.*
import com.vmedia.core.common.pure.obj.Period
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.*
import com.vmedia.core.sync.*
import com.vmedia.core.sync.SynchronizationDataType.*
import com.vmedia.core.sync.SynchronizationEvent.Data
import com.vmedia.core.sync.SynchronizationEvent.Error
import com.vmedia.core.sync.synchronizer.asset.AssetModel
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SynchronizationEventProducerImplTest {

    private val databaseDataSource: DatabaseDataSource = mock()

    private val assetEventExtractor: _AssetEventExtractor = mock()
    private val saleEventExtractor: _SaleEventExtractor = mock()
    private val downloadEventExtractor: _DownloadEventExtractor = mock()
    private val reviewEventExtractor: _ReviewEventExtractor = mock()
    private val revenueEventExtractor: _RevenueEventExtractor = mock()
    private val payoutEventExtractor: _PayoutEventExtractor = mock()

    private val producer: SynchronizationEventProducer
        get() = SynchronizationEventProducerImpl(
            databaseDataSource = databaseDataSource,
            assetEventExtractor = assetEventExtractor,
            saleEventExtractor = saleEventExtractor,
            downloadEventExtractor = downloadEventExtractor,
            reviewEventExtractor = reviewEventExtractor,
            revenueEventExtractor = revenueEventExtractor,
            payoutEventExtractor = payoutEventExtractor
        )

    @Before
    fun setUp() {
        whenever(databaseDataSource.putEvent(any(), any(), any()))
            .thenReturn(Completable.complete())

        assetEventExtractor.mockExtract(ASSET)
        saleEventExtractor.mockExtract(SALE)
        downloadEventExtractor.mockExtract(FREE_DOWNLOAD)
        reviewEventExtractor.mockExtract(REVIEW)
        revenueEventExtractor.mockExtract(REVENUE)
        payoutEventExtractor.mockExtract(PAYOUT)
    }

    @Test
    fun produce_notfinished() {
        // Given
        val emptyStatus = SynchronizationStatus(emptyMap())

        // When
        producer.produce(emptyStatus).blockingGet()

        // Then
        verify(assetEventExtractor, never()).extract(any())
        verify(databaseDataSource, never()).putEvent(any(), any(), any())
    }

    @Test
    fun produce_error() {
        // Given
        val errorStatus = SynchronizationStatus(
            mapOf(
                PUBLISHER to Error(Throwable()),
                ASSETS to Data(emptyList<AssetModel>()),
                PERIODS to Data(emptyList<Period>()),
                USERS to Data(emptyList<User>()),
                REVIEWS to Data(emptyList<Review>()),
                SALES to Data(emptyList<Sale>()),
                FREE_DOWNLOADS to Data(emptyList<Sale>()),
                REVENUES to Data(emptyList<Revenue>()),
                PAYOUTS to Data(emptyList<Payout>())
            )
        )

        // When
        producer.produce(errorStatus).blockingGet()

        // Then
        verify(assetEventExtractor, never()).extract(any())
        verify(saleEventExtractor, never()).extract(any())
        verify(downloadEventExtractor, never()).extract(any())
        verify(reviewEventExtractor, never()).extract(any())
        verify(revenueEventExtractor, never()).extract(any())
        verify(payoutEventExtractor, never()).extract(any())
        verify(databaseDataSource, never()).putEvent(any(), any(), any())
    }

    @Test
    fun produce_success() {
        // Given
        val wellStatus = SynchronizationStatus(
            mapOf(
                PUBLISHER to Data(Publisher(1L)),
                ASSETS to Data(emptyList<AssetModel>()),
                PERIODS to Data(emptyList<Period>()),
                USERS to Data(emptyList<User>()),
                REVIEWS to Data(emptyList<Review>()),
                SALES to Data(emptyList<Sale>()),
                FREE_DOWNLOADS to Data(emptyList<Sale>()),
                REVENUES to Data(emptyList<Revenue>()),
                PAYOUTS to Data(emptyList<Payout>())
            )
        )

        // When
        producer.produce(wellStatus).blockingGet()

        // Then
        verify(assetEventExtractor).extract(any())
        verify(saleEventExtractor).extract(any())
        verify(downloadEventExtractor).extract(any())
        verify(reviewEventExtractor).extract(any())
        verify(revenueEventExtractor).extract(any())
        verify(payoutEventExtractor).extract(any())
        verify(databaseDataSource, times(6 * EVENT_COUNT)).putEvent(any(), any(), any())
    }

    private companion object {

        private const val EVENT_COUNT = 3

        inline fun <reified T : Any> EventExtractor<T>.mockExtract(type: EventType) {
            whenever(extract(any())).thenReturn(
                Single.fromCallable {
                    val result = mutableListOf<EventModel>()
                    repeat(EVENT_COUNT) { result += EventModel(type) }
                    return@fromCallable result
                }
            )
        }

    }

}