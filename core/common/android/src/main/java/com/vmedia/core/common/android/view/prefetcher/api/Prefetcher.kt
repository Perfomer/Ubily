package com.vmedia.core.common.android.view.prefetcher.api

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface Prefetcher {

    fun setPrefetchedViewsCount(
        viewType: Int,
        count: Int,
        holderFactory: HolderFactory
    )

}

typealias HolderFactory = (fakeParent: ViewGroup, viewType: Int) -> RecyclerView.ViewHolder

interface RecycleViewToParentViewPoolOnDetach