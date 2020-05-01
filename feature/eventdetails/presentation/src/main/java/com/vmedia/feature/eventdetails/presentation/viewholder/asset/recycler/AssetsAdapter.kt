package com.vmedia.feature.eventdetails.presentation.viewholder.asset.recycler

import android.view.View
import androidx.annotation.LayoutRes
import com.vmedia.core.common.android.view.recycler.base.BaseAdapter

internal class AssetsAdapter<T : Any, VH : BaseAssetItemViewHolder<T>>(
    @LayoutRes private val itemLayoutResource: Int,
    private val viewHolderCreator: (view: View) -> VH
) : BaseAdapter<VH>() {

    var items: List<T> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount() = items.size

    override fun onLayoutRequested(viewType: Int) = itemLayoutResource

    override fun onCreateViewHolder(view: View, viewType: Int) = viewHolderCreator.invoke(view)

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])

}