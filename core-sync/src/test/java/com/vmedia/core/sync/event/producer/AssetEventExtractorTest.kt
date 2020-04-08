package com.vmedia.core.sync.event.producer

import com.vmedia.core.common.obj.EventType
import com.vmedia.core.data.internal.database.entity.Asset
import com.vmedia.core.sync.synchronizer.asset.AssetModel
import org.junit.Test

internal class AssetEventExtractorTest {

    private val extractor = AssetEventExtractor

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
        extractor.extract(MOCKY_ASSETS.take(1))
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.size == 1 }
            .assertValue { it[0].type == EventType.ASSET }
            .assertValue { it[0].entities == MOCKY_IDS.take(1) }
            .dispose()
    }

    @Test
    fun extract_many() {
        extractor.extract(MOCKY_ASSETS)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue { it.size == 1 }
            .assertValue { it[0].type == EventType.ASSET }
            .assertValue { it[0].entities == MOCKY_IDS }
            .dispose()
    }

    private companion object {

        private val MOCKY_IDS = listOf(1L, 5L, 10L, 15L, 99L)
        private val MOCKY_ASSETS = MOCKY_IDS.map(::createMockyAsset)

        private fun createMockyAsset(id: Long): AssetModel {
            return AssetModel(
                asset = Asset(id),
                images = emptyList(),
                keywords = emptySet()
            )
        }

    }

}