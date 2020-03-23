package robots

import com.upwork.automation.AppLibrary
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import java.lang.Exception
import java.util.concurrent.TimeUnit

fun PlatformRobot.onLoginScreen(block: LoginScreenRobot.() -> Unit) {
    val robot = LoginScreenRobot(appLibrary)
    robot.block()
}

class LoginScreenRobot(
    val appLibrary: AppLibrary,
    val driver: AppiumDriver<MobileElement> = appLibrary.driver
) {

    private val userNameInput = "id:login_username"
    private val continueButton = "xpath://button[text()='Continue']"
    private val passwordInput = "id:login_password"
    private val loginButton = "xpath://button[text()='Log In']"
    private val deviceAuthorization = "id:login_deviceAuthorization_answer"
    private val deviceAuthorizationContinue = "xpath://footer//button[text()='Continue']"

    fun verifyUI() {
        // TODO
    }

    fun enterUsername(userNameVal: String) {
        appLibrary.enterText(driver, userNameInput, userNameVal)
    }

    fun clickContinue() {
        appLibrary.clickMobileElement(driver, continueButton)
    }

    fun enterPassword(passwordVal: String) {
        appLibrary.enterText(driver, passwordInput, passwordVal)
    }

    fun clickLogin() {
        appLibrary.clickMobileElement(driver, loginButton)
    }

    fun forgotPassword() {

    }

    fun login(userNameVal: String, passwordVal: String): HomeScreenRobot {

        appLibrary.waitForContextLoading(driver, "WEBVIEW_")
        appLibrary.switchContextWithID(driver, 1)

        enterUsername(userNameVal)
        clickContinue()
        enterPassword(passwordVal)
        clickLogin()

        handleDeviceAuthorization()

        driver.context("NATIVE_APP");
        appLibrary.clickMobileElement(driver, "name:Allow");
        appLibrary.sleep(5000);

        return HomeScreenRobot(appLibrary)

    }

    fun handleDeviceAuthorization() {

        appLibrary.sleep(2000)
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        try {
            driver.findElement(By.id(deviceAuthorization.replace("id:", "")))
            appLibrary.enterText(driver, deviceAuthorization, "strange!")
            appLibrary.clickMobileElement(driver, deviceAuthorizationContinue)
        } catch (e: Exception) {
            // no need to worry if not found
        } finally {
            driver.manage().timeouts().implicitlyWait(appLibrary.GLOBALTIMEOUT, TimeUnit.SECONDS)
        }

    }

}