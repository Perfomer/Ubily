package com.vmedia.core.common.android.view.prefetcher.api

import android.app.Activity
import android.util.SparseIntArray
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.factorInCreateTime
import com.vmedia.core.common.android.view.prefetcher.OffThreadViewCreator
import timber.log.Timber
import kotlin.math.max

class PrefetchRecycledViewPool(
    activity: Activity,
    private val maxRecycledViews: Int
) : RecyclerView.RecycledViewPool(), Prefetcher {

    private val offThreadViewCreator = OffThreadViewCreator(activity, ::putViewFromCreator)

    private val prefetchRegistry = SparseIntArray()
    private val createdRegistry = SparseIntArray()

    var listener: PrefetchedViewsCountListener? = null


    override fun setPrefetchedViewsCount(
        viewType: Int,
        count: Int,
        holderFactory: HolderFactory
    ) {
        require(count > 0) { "Prefetched count should be > 0" }

        offThreadViewCreator.setPrefetchBound(holderFactory, viewType, count)

        prefetchRegistry.put(viewType, max(prefetchRegistry.get(viewType), count))
    }

    override fun putRecycledView(scrap: RecyclerView.ViewHolder) {
        val viewType = scrap.itemViewType
        setMaxRecycledViews(viewType, maxRecycledViews)

        super.putRecycledView(scrap)

        val recyclerView = scrap.nestedRecyclerView?.get()

        if (recyclerView is RecycleViewToParentViewPoolOnDetach) {
            val oldRecycledViewPool = recyclerView.recycledViewPool
            recyclerView.setRecycledViewPool(this)
            val recycler = recyclerView.recycler
            recyclerView.layoutManager?.removeAndRecycleAllViews(recycler)
            recycler.clear()
            recyclerView.setRecycledViewPool(oldRecycledViewPool)
        }
    }

    override fun getRecycledView(viewType: Int): RecyclerView.ViewHolder? {
        val holder = super.getRecycledView(viewType)

        if (holder == null) {
            offThreadViewCreator.itemCreatedOutside(viewType)
            createdRegistry[viewType]++
            logUiThreadCreation(viewType)
        }

        return holder
    }

    override fun clear() {
        offThreadViewCreator.clear()
        super.clear()
    }


    fun start() {
        offThreadViewCreator.start()
        attachToPreventViewPoolFromClearing()
    }

    fun terminate() {
        offThreadViewCreator.terminate()
    }


    private fun putViewFromCreator(scrap: RecyclerView.ViewHolder, creationTimeNs: Long) {
        factorInCreateTime(scrap.viewType, creationTimeNs)
        putRecycledView(scrap)
        createdRegistry[scrap.viewType]++

        listener?.onViewCountChanged(calculatePrefetchedCount())
    }

    private fun calculatePrefetchedCount(): Int {
        var result = 0

        for (i in 0..createdRegistry.size()) {
            result += createdRegistry[i]
        }

        return result
    }

    private fun logUiThreadCreation(viewType: Int) {
        val created = createdRegistry[viewType]
        val prefetch = prefetchRegistry[viewType]

        if (created > prefetch) {
            val recycledViewCount = getRecycledViewCount(viewType)
            val viewTypeName = getViewTypeName(viewType)

            Timber.w("ViewPool cache miss: created=$created, prefetch=$prefetch, cached=$recycledViewCount, holder=$viewTypeName")
        }
    }

    private fun getViewTypeName(viewType: Int): String {
        return viewType.toString()
    }

    private companion object {

        operator fun SparseIntArray.set(key: Int, value: Int) = put(key, value)

    }

}

interface PrefetchedViewsCountListener {
    fun onViewCountChanged(count: Int)
}