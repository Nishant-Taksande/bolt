package com.upwork.automation.smoketests

import robots.onHomeScreen
import robots.onLandingScreen
import robots.onLoginScreen
import robots.platform
import com.upwork.automation.TestBase
import org.testng.annotations.Test


class LoginTestJW : TestBase("LoginTest") {

    @Test
    fun loginTest() {

        platform {

            setAppLibrary(launchApp())

            onLandingScreen {
                selectEnvironment()
                verifyUI()
                clickLogin()
            }
            onLoginScreen {
                verifyUI()
                login("free17", "strange!")
            }
            onHomeScreen {
                logout()
            }

        }

    }

}