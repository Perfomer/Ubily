package com.vmedia.core.network.api.entity.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Why Java? Because, unfortunately, SimpleXmlConverter doesn't work fine with Kotlin data classes.
 */
@Root(name = "channel", strict = false)
public class RssChannelModel {

    @Element(name = "pubDate")
    private String mPubDate;

    @Element(name = "title")
    private String mTitle;

    @Element(name = "description")
    private String mDescription;

    @Element(name = "link")
    private String mLink;

    @ElementList(inline = true, name = "items")
    private List<RssItemModel> mItems;

    @Element(name = "image")
    private RssImageModel mImage;

    @Element(name = "language")
    private String mLanguage;

    @Element(name = "copyright")
    private String mCopyright;

    @Element(name = "ttl")
    private String mTtl;

    public String getPubDate() {
        return mPubDate;
    }

    public void setPubDate(String pubDate) {
        this.mPubDate = pubDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public List<RssItemModel> getItems() {
        return mItems;
    }

    public void setItems(List<RssItemModel> items) {
        this.mItems = items;
    }

    public RssImageModel getImage() {
        return mImage;
    }

    public void setImage(RssImageModel image) {
        this.mImage = image;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        this.mLanguage = language;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        this.mCopyright = copyright;
    }

    public String getTtl() {
        return mTtl;
    }

    public void setTtl(String ttl) {
        this.mTtl = ttl;
    }

}