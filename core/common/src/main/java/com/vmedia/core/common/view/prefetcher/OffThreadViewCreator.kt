package com.vmedia.core.common.view.prefetcher

import android.app.Activity
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import android.util.SparseIntArray
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.*
import com.vmedia.core.common.util.set
import timber.log.Timber
import java.lang.ref.WeakReference

private const val TYPE_ITEMS_CREATED_OUTSIDE = 0
private const val TYPE_ENQUEUE_BATCH = 1
private const val TYPE_CREATE_ITEM = 2
private const val TYPE_CLEAR = 3

private const val URGENT_PRIORITY = 0L
private const val HIGH_PRIORITY = 1L
private const val MID_PRIORITY = 2L
private const val LOW_PRIORITY = 3L

private const val THREAD_NAME = "ViewPrefetcherThread"

private typealias HolderCreator = (fakeParent: ViewGroup, viewType: Int) -> RecyclerView.ViewHolder
private typealias HolderConsumer = (holder: RecyclerView.ViewHolder, creationTimeNs: Long) -> Unit

internal class OffThreadViewCreator(
    activity: Activity,
    private val holderConsumer: HolderConsumer,
    private val thread: HandlerThread = HandlerThread(THREAD_NAME, THREAD_PRIORITY_BACKGROUND),
    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
) {

    // fakeParent is necessary to allow parsing MarginLayoutParams from xml
    private val fakeParent by lazy { LinearLayout(activity) }

    private val itemsCreated = SparseIntArray()
    private val itemsQueued = SparseIntArray()

    private val handler by lazy {
        start()
        Handler(thread.looper) { msg ->
            @Suppress("UNCHECKED_CAST")
            when (msg.what) {
                TYPE_ITEMS_CREATED_OUTSIDE -> createdOutside(msg.arg1, msg.arg2)
                TYPE_ENQUEUE_BATCH -> enqueueBatch(msg.obj as HolderCreator, msg.arg1, msg.arg2)
                TYPE_CREATE_ITEM -> createItem(msg.obj as HolderCreator, msg.arg1)
                TYPE_CLEAR -> clearAndStop()
            }
            true
        }
    }

    private var terminated = false


    fun start() {
        if (terminated) throw IllegalStateException("Prefetched already terminated")
        else if (thread.state == Thread.State.NEW) thread.start()
    }

    fun clear(): Boolean {
        return handler.sendMessageAtTime(
            handler.obtainMessage(TYPE_CLEAR),
            URGENT_PRIORITY
        )
    }

    fun terminate() {
        terminated = true
        thread.quit()
    }

    fun setPrefetchBound(holderCreator: HolderCreator, viewType: Int, count: Int): Boolean {
        return handler.sendMessageAtTime(
            handler.obtainMessage(TYPE_ENQUEUE_BATCH, viewType, count, holderCreator),
            MID_PRIORITY
        )
    }

    fun itemCreatedOutside(viewType: Int, count: Int = 1): Boolean {
        return handler.sendMessageAtTime(
            handler.obtainMessage(TYPE_ITEMS_CREATED_OUTSIDE, viewType, count),
            HIGH_PRIORITY
        )
    }


    private fun createdOutside(viewType: Int, count: Int) {
        itemsCreated[viewType] += count
    }

    private fun enqueueBatch(holderCreator: HolderCreator, viewType: Int, count: Int) {
        if (itemsQueued[viewType] >= count) return
        itemsQueued[viewType] = count

        val created = itemsCreated[viewType]

        if (created >= count) return

        enqueueItemCreation(holderCreator, viewType)
    }

    private fun createItem(holderCreator: HolderCreator, viewType: Int) {
        val created = itemsCreated[viewType] + 1
        val queued = itemsQueued[viewType]

        if (created > queued) return

        val holder: RecyclerView.ViewHolder
        val nestedRecyclerView: WeakReference<RecyclerView>?
        val start: Long
        val end: Long

        try {
            start = nanoTimeIfNeed()
            holder = holderCreator(fakeParent, viewType)

            nestedRecyclerView =
                if (ALLOW_THREAD_GAP_WORK) {
                    findNestedRecyclerView(holder.itemView)?.let { WeakReference(it) }
                } else {
                    null
                }

            end = nanoTimeIfNeed()
        } catch (e: Exception) {
            Timber.e("Error while prefetching viewHolder for viewtype=$viewType ${e.message}")
            return
        }

        holder.viewType = viewType

        itemsCreated[viewType] = created

        mainThreadHandler.post {
            holder.nestedRecyclerView = nestedRecyclerView
            holderConsumer(holder, end - start)
        }

        if (created < queued) {
            enqueueItemCreation(holderCreator, viewType)
        }
    }

    private fun enqueueItemCreation(holderCreator: HolderCreator, viewType: Int) {
        handler.sendMessageAtTime(
            handler.obtainMessage(
                TYPE_CREATE_ITEM,
                viewType,
                0,
                holderCreator
            ), LOW_PRIORITY
        )
    }

    private fun clearAndStop() {
        handler.removeCallbacksAndMessages(null)
        itemsQueued.clear()
        itemsCreated.clear()
    }

    private fun nanoTimeIfNeed(): Long {
        return if (ALLOW_THREAD_GAP_WORK) System.nanoTime() else 0L
    }

}