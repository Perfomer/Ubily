package com.vmedia.feature.splash.presentation.mvi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class SplashState(
    val isLoading: Boolean = false,
    val error: Throwable? = null
): Parcelable
