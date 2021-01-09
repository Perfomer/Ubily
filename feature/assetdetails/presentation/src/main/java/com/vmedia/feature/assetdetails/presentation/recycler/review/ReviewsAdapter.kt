package com.vmedia.feature.assetdetails.presentation.recycler.review

import android.view.View
import com.vmedia.core.common.android.view.recycler.base.BaseAdapter
import com.vmedia.core.data.internal.database.model.ReviewDetailed
import com.vmedia.feature.assetdetails.presentation.AssetDetailsViewModel
import com.vmedia.feature.assetdetails.presentation.R

internal typealias OnAuthorClickListener = (authorId: Long) -> Unit

internal class ReviewsAdapter(
    private val onAuthorClick: OnAuthorClickListener
) : BaseAdapter<ReviewViewHolder>() {

    private var items: List<ReviewDetailed> = emptyList()
    private var isExpanded: Boolean = false

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() =
        if (isExpanded) items.size
        else minOf(items.size, AssetDetailsViewModel.MAX_COLLAPSED_REVIEWS_COUNT)

    override fun getItemId(position: Int) = items[position].id

    override fun onLayoutRequested(viewType: Int) = R.layout.assetdetails_item_review

    override fun onCreateViewHolder(view: View, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(view, ::onAuthorClick)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(
            review = items[position],
            showDivider = position != itemCount - 1
        )
    }

    fun setItems(items: List<ReviewDetailed>, isExpanded: Boolean) {
        this.items = items
        this.isExpanded = isExpanded

        notifyDataSetChanged()
    }

    private fun onAuthorClick(position: Int) {
        onAuthorClick.invoke(items[position].authorId)
    }
}
