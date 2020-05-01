package com.vmedia.core.sync.synchronizer.user

import com.vmedia.core.common.android.util.Mapper
import com.vmedia.core.data.internal.database.entity.User
import com.vmedia.core.network.entity.DetailedReviewDto

internal object UserMapper : Mapper<DetailedReviewDto, User> {

    override fun map(from: DetailedReviewDto): User {
        return User(name = from.authorName)
    }

}