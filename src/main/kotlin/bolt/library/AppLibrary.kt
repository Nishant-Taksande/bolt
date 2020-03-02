package bolt.library

import com.upwork.automation.Configuration
import java.util.concurrent.TimeUnit
import io.appium.java_client.MobileElement
import io.appium.java_client.AppiumDriver
import org.apache.commons.io.FileUtils
import org.openqa.selenium.By
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.Reporter
import java.io.File
import java.net.URL
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.OutputType
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.TakesScreenshot
import java.io.IOException
import java.util.*


class AppLibrary {

    val GLOBALTIMEOUT: Long = 100
    lateinit var driver: AppiumDriver<MobileElement>
    var config: Configuration = Configuration()

    fun launchDriver(): AppiumDriver<MobileElement> {

        var caps = DesiredCapabilities()

        val appDir = File(config.appDirectory)
        val app = File(appDir, config.appName)

        mapOf(
            "platformName" to config.platform,
            "platformVersion" to config.platformVersion,
            "deviceName" to config.deviceName,
            "deviceID" to config.deviceID,
            "app" to app.absolutePath,
            "sendKeyStrategy" to "setValue",
            "noResetValue" to false,
            "autoGrantPermissions" to true,
            "session-override" to true
        ).forEach { k, v -> caps.setCapability(k, v) }


        if (config.platform.equals("ios", true)) {
            caps.setCapability("automationName", "XCUITest")
            caps.setCapability("xcodeSigningId", "iPhone Developer")

        } else {
            caps.setCapability("unicodeKeyboard", true)
            caps.setCapability("resetKeyboard", true)
        }

        caps.capabilityNames.forEach {
            System.out.println(it + ":" + caps.getCapability(it))
        }
        System.out.println(config.url)

        driver = AppiumDriver(URL("http://" + config.url + "/wd/hub"), caps)
        driver.manage().timeouts().implicitlyWait(GLOBALTIMEOUT, TimeUnit.SECONDS)
        return driver

    }

    fun findElement(driver: AppiumDriver<MobileElement>, locatorString: String): MobileElement {

        val parts = locatorString.split(":".toRegex())
        val type = parts[0] // 004
        var locator = parts[1]
        if (parts.size > 2) {
            for (i in 2 until parts.size) {
                locator = locator + ":" + parts[i]
            }
        }

        Reporter.log("Finding element with logic: $locatorString", true)

        var element: MobileElement
        if (type == "id") {
            element = driver.findElement(By.id(locator))
        } else if (type == "name") {
            element = driver.findElement(By.name(locator))
        } else if (type == "class") {
            element = driver.findElement(By.className(locator))
        } else if (type == "link") {
            element = driver.findElement(By.linkText(locator))
        } else if (type == "partiallink") {
            element = driver.findElement(By.partialLinkText(locator))
        } else if (type == "css") {
            element = driver.findElement(By.cssSelector(locator))
        } else if (type == "xpath") {
            element = driver.findElement(By.xpath(locator))
        } else {
            throw RuntimeException("Please provide correct element locating strategy")
        }

        return element
    }

    fun closeBrowser() {
//        if (driver != null) {
        driver.quit()
        println("Closing the Browser Successfully")
//        }
    }

    @Throws(Exception::class)
    fun clickMobileElement(driver: AppiumDriver<MobileElement>, locator: String) {

        var i = 0
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
        while (i < 5) {
            try {
                findElement(driver, locator).click()
                break
            } catch (e: Exception) {
                i++
                Thread.sleep(3000)
            }

        }

        if (i == 5) {
            driver.manage().timeouts().implicitlyWait(GLOBALTIMEOUT, TimeUnit.SECONDS)
            throw Exception("Element not found:$locator")
        }

    }

    @Throws(Exception::class)
    fun enterText(driver: AppiumDriver<MobileElement>, locator: String, textVal: String) {
//        driver.manage().timeouts().implicitlyWait(GLOBALTIMEOUT, TimeUnit.SECONDS)
        findElement(driver, locator).click()
        findElement(driver, locator).clear()
        findElement(driver, locator).sendKeys(textVal)
    }

    fun sleep(milliSeconds: Long) {
        try {
            Thread.sleep(milliSeconds)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun printContexts(driver: AppiumDriver<MobileElement>): String {
        println("Printing Contexts")
        var contextName = String()
        val allContexts = driver.contextHandles
        for (handle in allContexts) {
            println(handle)
            contextName = handle as String
        }

        return contextName
    }

    @Throws(Exception::class)
    fun waitForContextLoading(driver: AppiumDriver<MobileElement>, expectedContext: String): Boolean {

        var i = 30
        while (i > 0) {
            val allContexts = driver.contextHandles
            for (context in allContexts) {
                println(context)
                if (context.contains(expectedContext)) {
                    sleep(500)
                    return true
                }
                sleep(1000)
            }
            i--
        }

        throw Exception("Webview $expectedContext was not loaded within 30 seconds")
    }

    @Throws(Exception::class)
    fun getWebViewParentID(driver: AppiumDriver<MobileElement>): String {
        val allContexts = driver.contextHandles
        for (context in allContexts) {
            println(context)
            if (context.contains("WEBVIEW")) {
                val test = context.split("[.]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                return test[0]
            }
        }

        throw Exception("WebView ParentID parent was not found in available contexts")
    }


    fun switchContext(driver: AppiumDriver<MobileElement>, contextTitle: String): AppiumDriver<MobileElement> {

        val allContexts = driver.contextHandles
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

        try {
            for (handle in allContexts) {
                println(handle)
                driver.context(handle)
                if (handle.contains("WEBVIEW_")) {
                    waitForPageToLoad(driver)

                    if (driver.findElement(By.tagName("title")).text.contains(contextTitle, true)) {
                        return driver
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            driver.manage().timeouts().implicitlyWait(GLOBALTIMEOUT, TimeUnit.SECONDS)
        }

        throw Exception("No Context Found with title: $contextTitle")
    }

    fun switchContextWithID(driver: AppiumDriver<MobileElement>, id: Int): AppiumDriver<MobileElement> {

        val allContexts = driver.contextHandles
        driver.context(allContexts.elementAt(id))
        return driver
    }

    fun waitForPageToLoad(driver: AppiumDriver<MobileElement>) {
        WebDriverWait(driver, GLOBALTIMEOUT).until { webDriver ->
            (webDriver as JavascriptExecutor)
                .executeScript("return document.readyState") == "complete"
        }
    }

    @Throws(IOException::class)
    fun getScreenshot(name: String) {
        val path = "screenshots/$name"
        val src = (driver as TakesScreenshot).getScreenshotAs<File>(OutputType.FILE)
        FileUtils.copyFile(src, File(path))
        println("screenshot at :$path")
        Reporter.log("screenshot for $name available at :$path", true)
    }

    fun randInt(): Int {
        val min = 20
        val max = 32000
        val rand = Random()
        return (rand.nextInt(max - min + 1) + rand.nextInt(max - min + 1)) / 2
    }

}