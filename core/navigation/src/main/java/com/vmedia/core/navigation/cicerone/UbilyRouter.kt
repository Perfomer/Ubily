package com.vmedia.core.navigation.cicerone

import com.vmedia.core.navigation.cicerone.command.AddOver
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

internal class UbilyRouter : Router() {

    fun addOver(screen: Screen) {
        executeCommands(AddOver(screen))
    }

}