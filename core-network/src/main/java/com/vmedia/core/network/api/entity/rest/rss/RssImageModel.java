package com.vmedia.core.network.api.entity.rest.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Why Java? Because, unfortunately, SimpleXmlConverter doesn't work fine with Kotlin data classes.
 */
@Root(name = "image", strict = false)
class RssImageModel {

    @Element(name = "title")
    private String mTitle;

    @Element(name = "link")
    private String mLink;

    @Element(name = "url")
    private String mUrl;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

}