package com.vmedia.core.sync.event

import io.reactivex.Single

interface EventExtractor<T> {

    fun extract(source: T): Single<List<EventModel>>

}