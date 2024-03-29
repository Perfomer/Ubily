@file:Suppress("PackageDirectoryMismatch")

package androidx.recyclerview.widget

import android.view.View
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

internal class GapWorkerTaskPool : Iterable<GapWorker.Task> {

    private val tasksPool = ArrayList<GapWorker.Task>()

    private var tasksPoolHead = 0

    override fun iterator(): MutableIterator<GapWorker.Task> {
        return tasksPool.iterator()
    }

    fun buildTaskList(recyclerViews: Iterable<RecyclerView>) {
        var totalTaskCount = 0

        for (recyclerView in recyclerViews) {
            if (recyclerView.windowVisibility == View.VISIBLE) {
                recyclerView.mPrefetchRegistry.collectPrefetchPositionsFromView(recyclerView, false)
                totalTaskCount += recyclerView.mPrefetchRegistry.mCount
            }
        }

        resetPool(totalTaskCount)

        for (recyclerView in recyclerViews) {
            if (recyclerView.windowVisibility != View.VISIBLE) continue

            val prefetchRegistry = recyclerView.mPrefetchRegistry
            val velocity = abs(prefetchRegistry.mPrefetchDx) + abs(prefetchRegistry.mPrefetchDy)

            var i = 0

            while (i < prefetchRegistry.mCount * 2) {
                with(obtainTask()) {
                    distanceToItem = prefetchRegistry.mPrefetchArray[i + 1]
                    immediate = distanceToItem <= velocity
                    viewVelocity = velocity
                    view = recyclerView
                    position = prefetchRegistry.mPrefetchArray[i]
                }
                i += 2
            }
        }

        Collections.sort(tasksPool, GapWorker.sTaskComparator)
    }

    private fun obtainTask(): GapWorker.Task {
        return tasksPool.getOrNull(tasksPoolHead++) ?: GapWorker.Task().also { tasksPool.add(it) }
    }

    private fun resetPool(capacity: Int) {
        tasksPoolHead = 0
        tasksPool.ensureCapacity(capacity)
    }

}