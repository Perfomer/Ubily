package com.vmedia.core.data.internal.network.entity.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(strict = false)
data class RssRequest(
    @Element(name = "rss")
    val rss: RssModel,

    @Attribute(name = "version")
    val version: String
)