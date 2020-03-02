package com.vmedia.core.network.api.entity.rest.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
data class RssImageModel(
    @Element(name = "title") val title: String,
    @Element(name = "link") val link: String,
    @Element(name = "url") val url: String
)