package bolt.screens

import bolt.library.AppLibrary
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import org.openqa.selenium.By
import java.lang.Exception
import java.util.concurrent.TimeUnit

class LoginScreen(private var applib: AppLibrary) {

    private var driver: AppiumDriver<MobileElement> = applib.driver

    val userNameInput = "id:login_username"
    val continueButton = "xpath://button[text()='Continue']"
    val passwordInput = "id:login_password"
    val loginButton = "xpath://button[text()='Log In']"
    val deviceAuthorization = "id:login_deviceAuthorization_answer"
    val deviceAuthorizationContinue = "xpath://footer//button[text()='Continue']"

    fun login(userNameVal: String): HomeScreen {

        applib.waitForContextLoading(driver, "WEBVIEW_")
//        applib.switchContext(driver, "Log In - Upwork")
        applib.switchContextWithID(driver, 1)
        applib.enterText(driver, userNameInput, userNameVal)
        applib.clickMobileElement(driver, continueButton)
        applib.enterText(driver, passwordInput, "strange!")
        applib.clickMobileElement(driver, loginButton)

        handleDeviceAuthorization()

        driver.context("NATIVE_APP");
        applib.clickMobileElement(driver, "name:Allow");
        applib.sleep(5000);

        return HomeScreen(applib)

    }

    fun handleDeviceAuthorization() {

        applib.sleep(2000)
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        try {
            driver.findElement(By.id(deviceAuthorization.replace("id:", "")))
            applib.enterText(driver, deviceAuthorization, "strange!")
            applib.clickMobileElement(driver, deviceAuthorizationContinue)
        } catch (e: Exception) {
            // no need to worry if not found
        } finally {
            driver.manage().timeouts().implicitlyWait(applib.GLOBALTIMEOUT, TimeUnit.SECONDS)
        }

    }

}