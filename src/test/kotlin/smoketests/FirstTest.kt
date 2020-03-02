package com.upwork.automation

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit


class FirstTest {
    lateinit var driver: AppiumDriver<MobileElement>
    private val caps = DesiredCapabilities()

    @BeforeClass
    fun setup() {

        val appDir = File("apps")
        val app = File(appDir, "FreelancersDM-iphonesimulator-iOS13-1.7.0.7.app")

        caps.apply {
            setCapability("platformName", "ios")
        }

        mapOf(
            "platformName" to "ios",
            "platformVersion" to "13.2",
            "platformName" to "ios",
            "platformVersion" to "3.2",
            "deviceName" to "D8FD151C-634E-4FEA-A5BF-9FC3AE4C6A0D",
            "automationName" to "XCUITest",
            "app" to app.getAbsolutePath(),
            "xcodeSigningId" to "iPhone Developer",
            "session-override" to "true",
            "udid" to "D8FD151C-634E-4FEA-A5BF-9FC3AE4C6A0D",
            "sendKeyStrategy" to "setValue",
            "noResetValue" to "false",
            "autoGrantPermissions" to true

        ).forEach { k, v -> caps.setCapability(k, v) }

        driver = AppiumDriver<MobileElement>(URL("http://0.0.0.0:4723/wd/hub"), caps)
        driver.manage().timeouts().implicitlyWait(75, TimeUnit.SECONDS)

    }


    @Test
    fun myFirstTest() {
        println("Test")


    }

}