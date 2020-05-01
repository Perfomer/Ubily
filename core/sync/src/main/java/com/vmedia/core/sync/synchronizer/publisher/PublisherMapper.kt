package com.vmedia.core.sync.synchronizer.publisher

import com.vmedia.core.common.pure.util.Mapper
import com.vmedia.core.data.internal.database.entity.Publisher
import com.vmedia.core.network.entity.PublisherDto

internal object PublisherMapper : Mapper<Pair<Long, PublisherDto>, Publisher> {

    override fun map(from: Pair<Long, PublisherDto>): Publisher {
        val publisherId = from.first
        val dto = from.second

        return Publisher(
            id = publisherId,
            rssToken = dto.rssToken,
            name = dto.name,
            description = dto.description,
            url = dto.url,
            smallImageUrl = dto.smallImageUrl ?: "",
            largeImageUrl = dto.largeImageUrl ?: ""
        )
    }

}