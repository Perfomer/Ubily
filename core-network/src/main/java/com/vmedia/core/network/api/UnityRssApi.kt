package com.vmedia.core.network.api

import com.vmedia.core.network.api.entity.rest.rss.RssModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface UnityRssApi {

    @GET("/feed/{publisherActivityName}/{publisherActivityToken}/activity.rss")
    fun getCommentsRss(
        @Path("publisherActivityName") userName: String,
        @Path("publisherActivityToken") token: String
    ): Single<RssModel>

}