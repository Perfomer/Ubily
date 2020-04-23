package com.vmedia.feature.assetdetails.domain.model

import android.os.Parcelable
import com.vmedia.core.data.internal.database.entity.Keyword
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KeywordModel(
    val id: Long = 0L,
    val value: String = ""
) : Parcelable

internal fun Keyword.toModel(): KeywordModel {
    return KeywordModel(id, value)
}