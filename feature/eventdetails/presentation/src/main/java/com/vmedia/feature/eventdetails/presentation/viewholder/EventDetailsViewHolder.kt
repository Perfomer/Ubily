package com.vmedia.feature.eventdetails.presentation.viewholder

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import com.vmedia.core.common.android.obj.EventType
import com.vmedia.core.common.android.obj.event.EventInfo
import com.vmedia.core.common.android.util.inflate
import kotlinx.android.extensions.LayoutContainer

internal abstract class EventDetailsViewHolder<T : Any>(
    val eventType: EventType,
    protected val context: Context,
    @LayoutRes private val contentLayoutResource: Int
) : LayoutContainer {

    protected var currentItem: T? = null

    final override val containerView: View by lazy {
        context.inflate(contentLayoutResource)
    }

    @Suppress("UNCHECKED_CAST")
    fun bindEvent(event: EventInfo<*>) {
        currentItem = event.content as? T
        currentItem?.let(::bind)
    }

    protected abstract fun bind(item: T)

}