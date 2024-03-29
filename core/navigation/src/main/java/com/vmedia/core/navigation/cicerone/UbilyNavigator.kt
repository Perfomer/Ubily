package com.vmedia.core.navigation.cicerone

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.vmedia.core.navigation.cicerone.command.AddOver
import com.vmedia.core.navigation.cicerone.command.ForwardTagged
import com.vmedia.core.navigation.cicerone.command.Remove
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

internal class UbilyNavigator(
    activity: FragmentActivity,
    containerId: Int
) : SupportAppNavigator(activity, containerId) {

    override fun applyCommand(command: Command) {
        when (command) {
            is ForwardTagged -> forward(command)
            is AddOver -> addOver(command)
            is Remove -> remove(command)
            else -> super.applyCommand(command)
        }
    }

    private fun forward(command: ForwardTagged) {
        val screen = command.screen as SupportAppScreen
        val screenKey = screen.screenKey
        val fragment = createFragment(screen) ?: return

        fragmentManager.beginTransaction()
            .setup(fragment, command)
            .replace(containerId, fragment, screenKey)
            .addToBackStack(screenKey)
            .commit()

        localStackCopy.add(screenKey)
    }

    private fun addOver(command: AddOver) {
        val screen = command.screen as SupportAppScreen
        val screenKey = screen.screenKey
        val fragment = createFragment(screen) ?: return

        fragmentManager.beginTransaction()
            .setup(fragment, command)
            .add(containerId, fragment, screenKey)
            .addToBackStack(screenKey)
            .commit()

        localStackCopy.add(screenKey)
    }

    private fun remove(command: Remove) {
        val screenKey = command.screen.screenKey
        val fragment = fragmentManager.findFragmentByTag(screenKey) ?: return

        fragmentManager.beginTransaction()
            .remove(fragment)
            .commit()

        localStackCopy.remove(screenKey)
        fragmentManager.fragments.firstOrNull()?.onResume()
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