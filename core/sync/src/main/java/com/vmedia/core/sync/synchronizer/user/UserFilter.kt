package com.vmedia.core.sync.synchronizer.user

import com.vmedia.core.common.android.util.Filter
import com.vmedia.core.network.entity.DetailedReviewDto

object UserFilter : Filter<DetailedReviewDto> {

    override fun filter(source: List<DetailedReviewDto>): List<DetailedReviewDto> {
        return source.distinctBy(DetailedReviewDto::authorName)
    }

}