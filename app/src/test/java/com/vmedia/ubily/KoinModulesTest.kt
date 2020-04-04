package com.vmedia.ubily

import org.junit.Test
import org.junit.experimental.categories.Category
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules

@Category(CheckModuleTest::class)
class KoinModulesTest : KoinTest {

    @Test
    fun checkAllModules() = checkModules {
        modules(koinModules)
    }

}