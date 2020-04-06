package com.example.feature.feed.domain

import com.vmedia.core.data.datasource.DatabaseDataSource
import com.vmedia.core.data.obj.EventInfo
import io.reactivex.Observable

internal class FeedInteractor(
    private val dataSource: DatabaseDataSource
) {

    fun getEvents(): Observable<List<EventInfo>> {
        TODO()
    }

}