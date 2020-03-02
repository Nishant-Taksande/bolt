package com.upwork.automation

import bolt.library.AppLibrary
import org.testng.ITestContext
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.*
import java.io.File
import org.openqa.selenium.TakesScreenshot
import org.testng.ITestResult
import org.testng.Reporter
import org.testng.annotations.AfterMethod
import java.io.IOException
import java.lang.Exception


open class TestBase(val testName: String) {

    lateinit var suite: String
    lateinit var appLib: AppLibrary

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
        appLib.closeBrowser()
    }

    @AfterMethod
    fun checkFailure(result: ITestResult) {
        if (result.status == ITestResult.FAILURE) {
            try {
                var screenshotName = result.getName() + "_" + appLib.config.platform + "_" + appLib.randInt() + ".png"
                appLib.getScreenshot(screenshotName)

            } catch (e: Exception) {
                Reporter.log("Failed fetching URL and screenshot due to error:" + e.message, true)
                e.printStackTrace()
            }

            if (appLib.driver.sessionId != null) {
                Reporter.log("Session id for " + testName + " is " + appLib.driver.sessionId, true)
            }
        }
    }
}