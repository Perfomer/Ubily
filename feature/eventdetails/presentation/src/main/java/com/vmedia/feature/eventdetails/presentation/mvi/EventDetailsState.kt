package com.vmedia.feature.eventdetails.presentation.mvi

import android.os.Parcelable
import com.vmedia.core.common.pure.obj.event.EventInfo
import com.vmedia.core.common.pure.obj.event.EventInfo.StubEventInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class EventDetailsState(
    val isLoading: Boolean = false,
    val payload: EventInfo<*> = StubEventInfo,
    val error: Throwable? = null
) : Parcelable