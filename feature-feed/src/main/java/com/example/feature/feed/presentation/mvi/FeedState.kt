package com.example.feature.feed.presentation.mvi

import android.os.Parcelable
import com.vmedia.core.data.obj.EventInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class FeedState(
    val isLoading: Boolean = false,
    val payload: List<EventInfo> = emptyList(),
    val error: Throwable? = null
) : Parcelable