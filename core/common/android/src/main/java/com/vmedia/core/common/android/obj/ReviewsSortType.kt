package com.vmedia.core.common.android.obj

import androidx.annotation.StringRes
import com.vmedia.core.common.android.R
import com.vmedia.core.common.android.obj.ReviewsSortType.*

enum class ReviewsSortType {
    RELEVANCE,
    DATE_DESC,
    DATE_ASC,
    RATING_DESC,
    RATING_ASC;

    companion object {

        val labelResources: IntArray = values()
            .map(ReviewsSortType::labelResource)
            .toIntArray()

    }

}

val ReviewsSortType.labelResource: Int
    @StringRes
    get() = when (this) {
        RELEVANCE -> R.string.reviews_sort_relevance
        DATE_ASC -> R.string.reviews_sort_date_asc
        DATE_DESC -> R.string.reviews_sort_date_desc
        RATING_ASC -> R.string.reviews_sort_rating_asc
        RATING_DESC -> R.string.reviews_sort_rating_desc
    }