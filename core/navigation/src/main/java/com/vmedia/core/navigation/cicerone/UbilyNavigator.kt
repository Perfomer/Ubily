package com.vmedia.core.navigation.cicerone

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
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

        fragmentManager.beginTransaction()
            .setup(fragment, command)
            .add(containerId, fragment)
            .addToBackStack(screenKey)
            .commit()

        localStackCopy.add(screenKey)
    }

    private fun FragmentTransaction.setup(
        fragment: Fragment,
        command: Command
    ): FragmentTransaction {
        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            this
        )

        return this
    }

}