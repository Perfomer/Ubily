package com.vmedia.core.sync.synchronizer.category

import com.vmedia.core.common.android.util.Mapper
import com.vmedia.core.data.internal.database.entity.Category
import com.vmedia.core.network.api.entity.CategoryDto

object CategoryMapper : Mapper<CategoryDto, Category> {

    override fun map(from: CategoryDto): Category {
        return Category(
            id = from.id,
            name = from.name.substringAfterLast("/")
        )
    }

}