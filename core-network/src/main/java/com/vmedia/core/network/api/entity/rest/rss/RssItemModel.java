package com.vmedia.core.network.api.entity.rest.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Why Java? Because, unfortunately, SimpleXmlConverter doesn't work fine with Kotlin data classes.
 */
@Root(name = "item", strict = false)
public class RssItemModel {

    @Element(name = "title")
    private String mTitle;

    @Element(name = "link")
    private String mLink;

    @Element(name = "description")
    private String mDescription;

    @Element(name = "pubDate")
    private String mPubDate;

    @Element(name = "guid")
    private String mGuid;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPublishDate() {
        return mPubDate;
    }

    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }

    public String getGuid() {
        return mGuid;
    }

    public void setGuid(String guid) {
        mGuid = guid;
    }

}