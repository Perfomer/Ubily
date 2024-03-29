package com.vmedia.core.data.repository.event.mapper

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vmedia.core.common.pure.obj.EventType
import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class AssetMapperTest {

    private val source: DatabaseDataSource = mock()

    private val mapper: AssetMapper
        get() = AssetMapper(source)

    @Test
    fun map_empty() {
        // Given
        whenever(source.getEventAssets(any())).thenReturn(Single.just(emptyList()))

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
        whenever(source.getEventAssets(any())).thenReturn(Single.just(MOCKY_ASSETS))

        // When
        val mapping = mapper.map(MOCKY_EVENT)

        // Then
        mapping.test()
            .assertComplete()
            .assertValue { it.id == MOCKY_EVENT.id }
            .assertValue { it.date == MOCKY_EVENT.date }
            .assertValue { it.content.size == MOCKY_ASSETS.size }
            .dispose()
    }

    private companion object {

        private const val ASSET_COUNT = 10

        private val MOCKY_EVENT = Event(type = EventType.ASSET)

        private val MOCKY_ASSETS by lazy {
            val list = mutableListOf<Asset>()
            repeat(ASSET_COUNT) { list += createAsset(it.toLong()) }
            return@lazy list
        }

        private fun createAsset(id: Long): Asset {
            return Asset(id = id)
        }

    }

}