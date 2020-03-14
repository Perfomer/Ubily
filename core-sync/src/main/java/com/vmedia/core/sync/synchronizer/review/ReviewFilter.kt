package com.vmedia.core.sync.synchronizer.review

import com.vmedia.core.common.util.ItemFilter
import com.vmedia.core.data.internal.database.entity.Review
import com.vmedia.core.sync._ReviewProvider

internal class ReviewFilter(
    private val reviewProvider: _ReviewProvider
) : ItemFilter<Review>() {

    override fun filter(item: Review): Boolean {
        val oldReview = reviewProvider.invoke(item.authorId, item.assetId) ?: return true

        return item.comment != oldReview.comment || item.publisherReply != oldReview.publisherReply
    }

}