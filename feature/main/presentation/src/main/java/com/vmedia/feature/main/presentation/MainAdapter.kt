package com.vmedia.feature.main.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vmedia.feature.assetlist.api.BEAN_FRAGMENT_ASSETLIST
import com.vmedia.feature.feed.api.BEAN_FRAGMENT_FEED
import com.vmedia.feature.main.api.MainNavigatorScreen
import com.vmedia.feature.publisherdetails.api.BEAN_FRAGMENT_PUBLISHERDETAILS
import org.koin.core.KoinComponent
import org.koin.core.error.NoBeanDefFoundException
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.get

internal class MainAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity), KoinComponent {

    private val items = MainNavigatorScreen.values()

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment {
        return items[position].getFragment()
    }

    private companion object {

        private val MainNavigatorScreen.beanQualifier: String
            get() = when (this) {
                MainNavigatorScreen.FEED -> BEAN_FRAGMENT_FEED
                MainNavigatorScreen.STATISTICS -> "TODO"
                MainNavigatorScreen.PUBLISHER -> BEAN_FRAGMENT_PUBLISHERDETAILS
                MainNavigatorScreen.ASSETLIST -> BEAN_FRAGMENT_ASSETLIST
                MainNavigatorScreen.MENU -> "TODO"
            }

        // todo remove try catch when all fragment will be done
        private fun MainNavigatorScreen.getFragment(): Fragment {
            return try {
                get(Fragment::class.java, named(beanQualifier))
            } catch (ex: NoBeanDefFoundException) {
                Fragment()
            }
        }

    }

}