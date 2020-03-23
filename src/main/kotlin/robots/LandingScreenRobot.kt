package robots

import com.upwork.automation.AppLibrary
import com.upwork.automation.Configuration
import com.upwork.automation.PlatformTouchAction
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.touch.offset.PointOption

fun PlatformRobot.onLandingScreen(block: LandingScreenRobot.() -> Unit) {
    val robot = LandingScreenRobot(appLibrary)
    robot.block()
}

class LandingScreenRobot(
    val appLibrary: AppLibrary,
    val driver: AppiumDriver<MobileElement> = appLibrary.driver
) {

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

    fun selectEnvironment(env: String = appLibrary.config.appEnvironment) {

        var conf = Configuration();

        val logo: MobileElement =
            appLibrary.findElement(driver, (if (conf.platform.equals("android", true)) andLogo else iosLogo))
        logo.click()
        appLibrary.sleep(1000)

        var action = PlatformTouchAction(driver)
        val d = driver.manage().window().size
        val p = PointOption.point(d.width / 2, (0.15 * d.height).toInt())
        println("Screen Dimensions:Height-" + d.height.toString() + ":Width-" + d.width)

        for (i in 1..5) {
            action.tap(p).perform()
        }
        appLibrary.sleep(1000)

        if (conf.platform.equals("android", true)) {

            appLibrary.clickMobileElement(driver, andEnableNativeMenus)
            appLibrary.clickMobileElement(driver, andEnvironmentSelect)
            appLibrary.clickMobileElement(driver, andOptions.replace("Replace", env))
            appLibrary.clickMobileElement(driver, andSetButton)
            appLibrary.clickMobileElement(driver, andNavigationButton)
            appLibrary.sleep(1000)
            driver.terminateApp(andActivity)
            appLibrary.sleep(1000)
            driver.activateApp(andActivity)
            appLibrary.sleep(1000)

        } else {

            appLibrary.clickMobileElement(driver, iosEnableDebug)
            appLibrary.clickMobileElement(driver, iosDoneButton)
            appLibrary.clickMobileElement(driver, iosProdSettings)
            appLibrary.clickMobileElement(driver, iosOptions.replace("Replace", env))
            appLibrary.sleep(1000)
            driver.activateApp(iosActivity)
            appLibrary.sleep(1000)

        }

    }

    fun verifyUI() {
        if (appLibrary.config.appEnvironment.equals("ios")) {
            // TODO
        } else if (appLibrary.config.appEnvironment.equals("android")) {
            // TODO
        }
    }

    fun clickLogin(): LoginScreenRobot {

        if (appLibrary.config.platform.equals("android", true))
            appLibrary.clickMobileElement(driver, andLoginButton)
        else
            appLibrary.clickMobileElement(driver, iosLoginButton)

        return LoginScreenRobot(appLibrary)
    }
}
