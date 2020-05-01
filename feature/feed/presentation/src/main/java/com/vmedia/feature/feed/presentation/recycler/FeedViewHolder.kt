package com.vmedia.feature.feed.presentation.recycler

import android.view.View
import com.perfomer.blitz.setTimeAgo
import com.vmedia.core.common.android.obj.event.EventInfo
import com.vmedia.core.common.android.obj.iconResource
import com.vmedia.core.common.android.obj.titleResource
import com.vmedia.core.common.android.util.diffedValue
import com.vmedia.core.common.android.util.setEventDescription
import com.vmedia.core.common.android.view.recycler.base.BaseViewHolder
import kotlinx.android.synthetic.main.feed_item.*

internal abstract class FeedViewHolder<T : EventInfo<*>>(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.invokeWithPosition() }
        feed_item_options.setOnClickListener { onOptionsClick.invokeWithPosition() }
    }

    @Suppress("UNCHECKED_CAST")
    fun bind(event: EventInfo<*>) {
        val type = event.type

        feed_item_icon.setImageResource(type.iconResource)
        feed_item_title.diffedValue = getString(type.titleResource)
        feed_item_date.setTimeAgo(event.date, showSeconds = true)
        feed_item_description.setEventDescription(event)

        bindContent(event as T)
    }

    protected abstract fun bindContent(item: T)

}