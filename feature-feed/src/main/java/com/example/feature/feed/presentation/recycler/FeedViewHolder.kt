package com.example.feature.feed.presentation.recycler

import android.view.View
import com.vmedia.core.common.obj.iconResource
import com.vmedia.core.common.obj.titleResource
import com.vmedia.core.common.util.diffedValue
import com.vmedia.core.common.util.getTimeAgo
import com.vmedia.core.common.view.recycler.base.BaseViewHolder
import com.vmedia.core.data.obj.EventInfo
import kotlinx.android.synthetic.main.feed_item.*

internal abstract class FeedViewHolder<T : EventInfo<*>>(
    containerView: View,
    onClick: (position: Int) -> Unit,
    onOptionsClick: (position: Int) -> Unit
) : BaseViewHolder(containerView) {

    init {
        containerView.setOnClickListener { onClick.safeInvoke(adapterPosition) }
        feed_item_options.setOnClickListener { onOptionsClick.safeInvoke(adapterPosition) }
    }

    @Suppress("UNCHECKED_CAST")
    fun bind(event: EventInfo<*>) {
        val type = event.type

        feed_item_icon.setImageResource(type.iconResource)
        feed_item_title.diffedValue = getString(type.titleResource)
        feed_item_date.diffedValue = context.getTimeAgo(event.date)

        bindContent(event as T)
    }

    protected abstract fun bindContent(item: T)

}