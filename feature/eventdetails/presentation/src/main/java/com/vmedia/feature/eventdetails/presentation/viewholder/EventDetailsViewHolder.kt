package com.vmedia.feature.eventdetails.presentation.viewholder

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import com.vmedia.core.common.obj.EventType
import com.vmedia.core.common.obj.event.EventInfo
import com.vmedia.core.common.util.inflate
import kotlinx.android.extensions.LayoutContainer

internal abstract class EventDetailsViewHolder<T : Any>(
    val eventType: EventType,
    protected val context: Context,
    @LayoutRes private val contentLayoutResource: Int
) : LayoutContainer {

    override val containerView: View by lazy {
        context.inflate(contentLayoutResource)
    }

    @Suppress("UNCHECKED_CAST")
    fun bindEvent(event: EventInfo<*>) {
        bind(event.content as T)
    }

    protected abstract fun bind(item: T)

}