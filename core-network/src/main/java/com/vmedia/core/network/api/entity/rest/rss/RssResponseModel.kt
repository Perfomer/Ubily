package com.vmedia.core.network.api.entity.rest.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(strict = false)
data class RssResponseModel(
    @Element(name = "rss") val rss: RssModel,
    @Attribute(name = "version") val version: String
)