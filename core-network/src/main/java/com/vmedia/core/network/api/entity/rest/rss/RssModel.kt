package com.vmedia.core.network.api.entity.rest.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class RssModel(
    @Element(name = "channel") val channel: RssChannelModel,
    @Attribute(name = "version") val version: String,
    @Attribute(name = "xmlns:blogChannel") val blogChannel: String
)