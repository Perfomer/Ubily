package com.vmedia.feature.main.presentation

import android.os.Bundle
import android.view.View
import com.vmedia.core.common.android.view.BaseFragment
import com.vmedia.feature.main.api.MainNavigatorScreen
import com.vmedia.feature.main.presentation.utils.initMainMenu
import com.vmedia.feature.main.presentation.utils.mainNavigator
import kotlinx.android.synthetic.main.main_fragment.*

internal class MainFragment : BaseFragment(R.layout.main_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_navigation.initMainMenu(mainNavigator, MainNavigatorScreen.FEED)
        main_pager.adapter = MainAdapter(baseActivity)
    }

    override fun onResume() {
        super.onResume()
        setNavigationBarDark(true)
    }

}