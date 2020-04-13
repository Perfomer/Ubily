package com.vmedia.feature.feed.presentation.mvi

import android.os.Parcelable
import com.vmedia.core.common.obj.event.EventInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class FeedState(
    val isLoading: Boolean = false,
    val payload: List<EventInfo<*>> = emptyList(),
    val error: Throwable? = null
) : Parcelable