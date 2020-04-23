package com.vmedia.core.data.internal.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.vmedia.core.common.obj.Rating
import com.vmedia.core.common.util.EMPTY_DATE
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class ReviewDetailed(
    val id: Long = 0,
    val authorId: Long = 0,
    val title: String = "",
    val comment: String = "",
    val publishingDate: Date = EMPTY_DATE,
    @Rating val rating: Int = 1,
    @ColumnInfo(name = "name") val authorName: String = "",
    @ColumnInfo(name = "reply_comment") val publisherReply: String? = null
) : Parcelable