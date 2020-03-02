package bolt.screens

import bolt.library.AppLibrary
import com.upwork.automation.Configuration
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import javax.swing.Spring.height
import io.appium.java_client.touch.offset.PointOption
import java.awt.SystemColor.window
import com.sun.awt.SecurityWarning.getSize
import io.appium.java_client.TouchAction
import org.openqa.selenium.interactions.touch.TouchActions


class LandingScreen(private var applib: AppLibrary) {

    private var driver: AppiumDriver<MobileElement> = applib.driver

    val andLogo = "id:com.upwork.android.apps.main:id/logo"
    val iosLogo = "name:upwork-launch"

    val iosEnableDebug = "name:Enable debug mode"
    val iosDoneButton = "id:Done"
    val iosProdSettings = "name:Prod"
    val iosStageSettings = "name:Stage"
    val iosStage = "xpath://XCUIElementTypeSheet[@name='Select an environment']//XCUIElementTypeButton[@name='Stage']"
    val iosActivity = "com.upwork.ios.apps.bolt"
    val iosLoginButton = "name:Log In"

    var andEnableNativeMenus = "id:com.upwork.android.apps.main:id/enableNativeMenus"
    val andEnvironmentSelect = "id:environment"
    val andOptions = "xpath://android.widget.TextView[@text='Replace']"
    val andSetButton = "id:set"
    val andNavigationButton = "id:navigationButton"
    val andActivity = "com.upwork.android.apps.main"
    val andLoginButton = "id:loginButton"


    fun selectEnvironement(env: String) {

        var conf = Configuration();

        if (conf.platform.equals("android", true)) {

            val logo: MobileElement = applib.findElement(driver, andLogo)
            logo.click()
            logo.click()
            logo.click()
            logo.click()
            logo.click()

            applib.clickMobileElement(driver, andEnableNativeMenus)
            applib.clickMobileElement(driver, andEnvironmentSelect)
            applib.clickMobileElement(driver, andOptions.replace("Replace", env))
            applib.clickMobileElement(driver, andSetButton)
            applib.clickMobileElement(driver, andNavigationButton)
            applib.sleep(1000)
            driver.terminateApp(andActivity)
            applib.sleep(1000)
            driver.activateApp(andActivity)
            applib.sleep(1000)

        } else {

            var logo = applib.findElement(driver, iosLogo)
            applib.sleep(5000)
            logo.click()
            logo.click()
            logo.click()
            logo.click()
            logo.click()
            logo.click()

//            val action = TouchActions(driver)
//            val d = driver.manage().window().size
//            println(d.height.toString() + ":" + d.width)
//            val x = d.width / 2
//            val y = (0.15 * d.height) as Int
//
//            for (i in 1..5) {
//                action.down(x,y).up(x,y).perform()
//            }

            applib.sleep(1000)
            applib.clickMobileElement(driver, iosEnableDebug)
            applib.clickMobileElement(driver, iosDoneButton)
            applib.sleep(1000)
            driver.activateApp(iosActivity)
            applib.sleep(1000)

        }

    }

    fun clickLogin(): LoginScreen {

        if (applib.config.platform.equals("android", true))
            applib.clickMobileElement(driver, andLoginButton)
        else
            applib.clickMobileElement(driver, iosLoginButton)

        return LoginScreen(applib)
    }
}