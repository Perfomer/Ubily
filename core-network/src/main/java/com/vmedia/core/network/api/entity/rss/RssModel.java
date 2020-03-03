package com.vmedia.core.network.api.entity.rss;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Why Java? Because, unfortunately, SimpleXmlConverter doesn't work fine with Kotlin data classes.
 */
@Root(name = "rss", strict = false)
public class RssModel {

    @Element(name = "channel")
    private RssChannelModel mChannel;

    @Attribute(name = "version")
    private String mVersion;

    public RssChannelModel getChannel() {
        return mChannel;
    }

    public void setChannel(RssChannelModel channel) {
        this.mChannel = channel;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        this.mVersion = version;
    }

}