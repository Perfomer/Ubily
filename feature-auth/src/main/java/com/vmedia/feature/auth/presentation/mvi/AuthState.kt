package com.vmedia.feature.auth.presentation.mvi

import android.os.Parcelable
import com.vmedia.feature.auth.presentation.AuthInputError
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class AuthState(
    val login: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val inputErrors: List<AuthInputError> = emptyList()
) : Parcelable