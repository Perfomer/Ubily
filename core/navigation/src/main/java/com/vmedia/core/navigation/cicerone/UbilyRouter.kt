package com.vmedia.core.navigation.cicerone

import com.vmedia.core.navigation.cicerone.command.AddOver
import com.vmedia.core.navigation.cicerone.command.ForwardTagged
import com.vmedia.core.navigation.cicerone.command.Remove
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

internal class UbilyRouter : Router() {

    fun addOver(screen: Screen) {
        executeCommands(AddOver(screen))
    }

    fun remove(screen: Screen) {
        executeCommands(Remove(screen))
    }

    fun forward(screen: Screen) {
        executeCommands(ForwardTagged(screen))
    }

}