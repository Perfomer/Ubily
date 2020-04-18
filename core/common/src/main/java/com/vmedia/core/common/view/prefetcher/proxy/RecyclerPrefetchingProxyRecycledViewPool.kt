@file:Suppress("PackageDirectoryMismatch")

package androidx.recyclerview.widget

import com.vmedia.core.common.view.prefetcher.api.PrefetchRecycledViewPool

/**
 * It's necessary to call RecycledViewPool.attach() before any RecyclerView actual attaching
 *      to prevent RecycledViewPool with already prefetched items from clearing
 *      on initial setAdapter() call.
 * Check [RecyclerView.onAdapterChanged] for additional info.
 */
internal fun RecyclerView.RecycledViewPool.attachToPreventViewPoolFromClearing() {
    this.attach()
}

internal fun PrefetchRecycledViewPool.factorInCreateTime(viewType: Int, createTimeNs: Long) {
    val recycledViewPool = this as RecyclerView.RecycledViewPool
    recycledViewPool.factorInCreateTime(viewType, createTimeNs)
}