package com.upwork.automation.smoketests

import bolt.library.AppLibrary
import bolt.screens.LandingScreen
import com.upwork.automation.TestBase
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class LoginTest : TestBase("LoginTest") {

    @BeforeClass
    fun setUp() {
        appLib = AppLibrary()
        appLib.launchDriver()
    }

    @Test
    fun loginTest() {
        var ls = LandingScreen(appLib)
        ls.selectEnvironment(appLib.config.appEnvironment)
        ls.clickLogin().login("free2")

    }

}