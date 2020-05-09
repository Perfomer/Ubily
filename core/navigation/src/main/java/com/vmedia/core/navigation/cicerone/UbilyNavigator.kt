package com.vmedia.core.navigation.cicerone

import androidx.fragment.app.FragmentActivity
import com.vmedia.core.navigation.cicerone.command.AddOver
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

internal class UbilyNavigator(
    activity: FragmentActivity,
    containerId: Int
) : SupportAppNavigator(activity, containerId) {

    override fun applyCommand(command: Command) {
        when (command) {
            is AddOver -> addOver(command)
            else -> super.applyCommand(command)
        }
    }

    private fun addOver(command: AddOver) {
        val screen = command.screen as SupportAppScreen
        val screenKey = screen.screenKey

        val fragment = createFragment(screen) ?: return
        val transaction = fragmentManager.beginTransaction()

        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            transaction
        )

        transaction
            .add(containerId, fragment)
            .addToBackStack(screenKey)
            .commit()

        localStackCopy.add(screenKey)
    }

}