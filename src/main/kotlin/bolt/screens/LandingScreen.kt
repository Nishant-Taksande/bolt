package bolt.screens

import bolt.library.AppLibrary
import bolt.library.PlatformTouchAction
import com.upwork.automation.Configuration
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.ActionOptions
import io.appium.java_client.touch.offset.PointOption
import org.openqa.selenium.interactions.touch.TouchActions
import javax.swing.Spring.height




class LandingScreen(private var applib: AppLibrary) {

    private var driver: AppiumDriver<MobileElement> = applib.driver

    val andLogo = "id:com.upwork.android.apps.main:id/logo"
    val iosLogo = "name:upwork-launch"

    val iosEnableDebug = "name:Enable debug mode"
    val iosDoneButton = "id:Done"
    val iosProdSettings = "name:Prod"
    val iosStageSettings = "name:Stage"
    val iosOptions = "xpath://XCUIElementTypeScrollView//XCUIElementTypeButton[@name='Replace']"
    val iosActivity = "com.upwork.ios.apps.bolt"
    val iosLoginButton = "name:Log In"

    var andEnableNativeMenus = "id:com.upwork.android.apps.main:id/enableNativeMenus"
    val andEnvironmentSelect = "id:environment"
    val andOptions = "xpath://android.widget.TextView[@text='Replace']"
    val andSetButton = "id:set"
    val andNavigationButton = "id:navigationButton"
    val andActivity = "com.upwork.android.apps.main"
    val andLoginButton = "id:loginButton"


    fun selectEnvironment(env: String) {

        var conf = Configuration();

        val logo: MobileElement =
            applib.findElement(driver, (if (conf.platform.equals("android", true)) andLogo else iosLogo))
        logo.click()
        applib.sleep(1000)

        var action = PlatformTouchAction(driver)
        val d = driver.manage().window().size
        val p = PointOption.point(d.width / 2, (0.15 * d.height).toInt())
        println("Screen Dimensions:Height-" + d.height.toString() + ":Width-" + d.width)

        for (i in 1..5) {
            action.tap(p).perform()
        }
        applib.sleep(1000)

        if (conf.platform.equals("android", true)) {

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

            applib.clickMobileElement(driver, iosEnableDebug)
            applib.clickMobileElement(driver, iosDoneButton)
            applib.clickMobileElement(driver, iosProdSettings)
            applib.clickMobileElement(driver, iosOptions.replace("Replace", env))
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