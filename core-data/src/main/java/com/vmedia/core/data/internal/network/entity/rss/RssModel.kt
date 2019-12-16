package com.vmedia.core.data.internal.network.entity.rss

import com.vmedia.core.data.internal.network.entity.rss.RssChannelModel
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class RssModel(
    @Element(name = "channel")
    val channel: RssChannelModel,
    @Attribute(name = "version")
    val version: String
)