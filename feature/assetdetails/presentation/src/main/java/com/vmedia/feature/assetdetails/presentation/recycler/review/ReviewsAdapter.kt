package com.vmedia.feature.assetdetails.presentation.recycler.review

import android.view.View
import com.vmedia.core.common.view.recycler.base.BaseAdapter
import com.vmedia.core.common.view.recycler.diffedListBy
import com.vmedia.core.data.internal.database.model.ReviewDetailed
import com.vmedia.feature.assetdetails.presentation.R

internal class ReviewsAdapter(
    private val onAuthorClick: (authorId: Long) -> Unit
) : BaseAdapter<ReviewViewHolder>() {

    var items by diffedListBy(ReviewDetailed::id)


    override fun onLayoutRequested(viewType: Int) = R.layout.assetdetails_item_review

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(view: View, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(view, ::onAuthorClick)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(
            review = items[position],
            showDivider = position != itemCount - 1
        )
    }

    private fun onAuthorClick(position: Int) {
        onAuthorClick.invoke(items[position].authorId)
    }

}