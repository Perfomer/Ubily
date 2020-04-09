package com.vmedia.ubily

import android.os.Build
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Category(CheckModuleTest::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class KoinModulesTest : KoinTest {

    @Test
    fun checkAllModules() = checkModules {
        modules(koinModules)
    }

}