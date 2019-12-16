package com.vmedia.core.data.internal.network.entity.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class RssItemModel(
    @Element(name = "title")
    val title: String,
    @Element(name = "link")
    val link: String,
    @Element(name = "description")
    val description: String,
    @Element(name = "pubDate")
    val publishDate: String,
    @Element(name = "guid")
    val guid: String
)