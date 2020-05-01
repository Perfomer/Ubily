package com.vmedia.core.common.android.util

import androidx.annotation.StringRes
import com.vmedia.core.common.android.R
import com.vmedia.core.common.pure.obj.ReviewsSortType

val reviewsSortTypeLabelResources: IntArray
    get() = ReviewsSortType.values()
        .map(ReviewsSortType::labelResource)
        .toIntArray()

val ReviewsSortType.labelResource: Int
    @StringRes
    get() = when (this) {
        ReviewsSortType.RELEVANCE -> R.string.reviews_sort_relevance
        ReviewsSortType.DATE_ASC -> R.string.reviews_sort_date_asc
        ReviewsSortType.DATE_DESC -> R.string.reviews_sort_date_desc
        ReviewsSortType.RATING_ASC -> R.string.reviews_sort_rating_asc
        ReviewsSortType.RATING_DESC -> R.string.reviews_sort_rating_desc
    }