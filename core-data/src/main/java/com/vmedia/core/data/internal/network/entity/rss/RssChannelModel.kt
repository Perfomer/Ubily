package com.vmedia.core.data.internal.network.entity.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class RssChannelModel(
    @Element(name = "pubDate") val pubDate: String,
    @Element(name = "title") val title: String,
    @Element(name = "description") val description: String,
    @Element(name = "link") val link: String,
    @ElementList(inline = true, name = "item") val items: List<RssItemModel>,
    @Element(name = "image") val image: RssImageModel,
    @Element(name = "language") val language: String,
    @Element(name = "copyright") val copyright: String,
    @Element(name = "ttl") val ttl: String
)