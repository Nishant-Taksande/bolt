package com.upwork.automation

import org.testng.Reporter
import java.io.File
import java.io.FileInputStream
import java.util.*

class Configuration {

    var runFrom: String       //local, jenkins
    var runOn: String         //local, SauceLabs, BrowserStack, Grid
    var appEnvironment: String
    var platform: String
    var appDirectory: String
    var appName: String
    var deviceName: String
    var deviceID: String
    var platformVersion: String
    var url: String

    init {
        val prop = Properties()

        try {
            prop.load(FileInputStream(File("config.properties")))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        this.runFrom =
                if (System.getProperty("runFrom").isNullOrBlank()) prop.getProperty("runFrom") else System.getProperty("runFrom")
        this.runOn =
                if (System.getProperty("runOn").isNullOrBlank()) prop.getProperty("runOn") else System.getProperty("runOn")
        this.platform =
                if (System.getProperty("platform").isNullOrBlank()) prop.getProperty("platform") else System.getProperty(
                    "platform"
                )
        this.appEnvironment =
                if (System.getProperty("appEnvironment").isNullOrBlank()) prop.getProperty("appEnvironment") else System.getProperty(
                    "appEnvironment"
                )

        this.appDirectory =
                if (System.getProperty("appDirectory").isNullOrBlank()) prop.getProperty("appDirectory") else System.getProperty(
                    "appDirectory"
                )
        this.appName =
                if (System.getProperty("appName").isNullOrBlank()) prop.getProperty("appName") else System.getProperty("appName")
        this.deviceName =
                if (System.getProperty("deviceName").isNullOrBlank()) prop.getProperty("deviceName") else System.getProperty(
                    "deviceName"
                )
        this.deviceID =
                if (System.getProperty("deviceID").isNullOrBlank()) prop.getProperty("deviceID") else System.getProperty(
                    "deviceID"
                )
        this.platformVersion =
                if (System.getProperty("platformVersion").isNullOrBlank()) prop.getProperty("platformVersion") else System.getProperty(
                    "platformVersion"
                )
        this.url = if (System.getProperty("url").isNullOrBlank()) prop.getProperty("url") else System.getProperty("url")

        Reporter.log("runFrom: $runFrom")
        Reporter.log("runFrom: $runOn")
        Reporter.log("runFrom: $platform")
        Reporter.log("runFrom: $appDirectory")
        Reporter.log("runFrom: $appName")
        Reporter.log("runFrom: $deviceName")
        Reporter.log("runFrom: $deviceID")
        Reporter.log("runFrom: $platformVersion")
        Reporter.log("runFrom: $url")
    }

}