package com.example.feature.feed.domain.mapper

import com.example.feature.feed.domain.FeedRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.data.internal.database.entity.Event
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class AssetMapperTest {

    private val repository: FeedRepository = mock()

    private val mapper: AssetMapper
        get() = AssetMapper(repository)

    @Test
    fun map_empty() {
        // Given
        whenever(repository.getEventAssets(any())).thenReturn(Single.just(emptyList()))

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
        whenever(repository.getEventAssets(any())).thenReturn(Single.just(MOCKY_ASSETS))

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