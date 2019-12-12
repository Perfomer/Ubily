package com.vmedia.feature.auth.presentation.mvi

import com.vmedia.feature.auth.presentation.AuthInputError

internal data class AuthState(
    val login: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val inputErrors: List<AuthInputError> = emptyList()
)