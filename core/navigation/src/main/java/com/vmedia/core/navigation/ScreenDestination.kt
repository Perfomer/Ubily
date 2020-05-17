package com.vmedia.core.navigation

import androidx.fragment.app.Fragment
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class ScreenDestination(
    private val name: String,
    vararg args: Any
) : SupportAppScreen(), KoinComponent {

    private val definitionParameters by lazy { parametersOf(*args) }

    override fun getFragment() = get<Fragment>(named(name)) { definitionParameters }

}