package com.upwork.automation

import org.testng.ITestContext
import org.testng.ITestResult
import org.testng.Reporter
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.*


open class TestBase(val testName: String) {

    lateinit var suite: String
    private lateinit var appLibrary: AppLibrary

    fun setAppLibrary(appLibrary: AppLibrary) {
        this.appLibrary = appLibrary
    }

    @BeforeClass(alwaysRun = true)
    fun setUp(context: ITestContext) {
        suite = context.getCurrentXmlTest().getSuite().getName();
        suite = if (suite != "" && !suite.equals("Default suite")) suite else InetAddress.getLocalHost().hostName

        val date = Date()
        val sdf = SimpleDateFormat("MMMddyyyyhhmmssaz")
        val currentDate = sdf.format(date)

        if (System.getProperty("Build") == null) {
            System.setProperty("Build", suite + "_" + currentDate)
            System.setProperty("Suite", suite)
        }
        System.setProperty("Test", testName)

        System.out.println("BuildName:" + System.getProperty("BuildName"))
        System.out.println("Suite: $suite")
        System.out.println("TestName: $testName")
        System.out.println("")

    }

    @AfterClass(alwaysRun = true)
    fun tearDown() {
        appLibrary.closeApp()
    }

    @AfterMethod
    fun checkFailure(result: ITestResult) {

        if (result.status == ITestResult.FAILURE) {
            try {
                var screenshotName =
                    testName + "_" + result.getName() + "_" + appLibrary.config.platform + "_" + appLibrary.randInt() + ".png"
                appLibrary.getScreenshot(screenshotName)

            } catch (e: Exception) {
                Reporter.log("Failed fetching URL and screenshot due to error:" + e.message, true)
                e.printStackTrace()
            }

            if (appLibrary.driver.sessionId != null) {
                Reporter.log("Session id for " + testName + " is " + appLibrary.driver.sessionId, true)
            }
        }
    }
}