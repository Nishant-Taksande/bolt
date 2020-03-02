package bolt.screens

import bolt.library.AppLibrary
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement

class HomeScreen(private var applib: AppLibrary) {

    private var driver: AppiumDriver<MobileElement> = applib.driver

    val userNameInput = "id:login_username"
    val continueButton = "xpath://button[text()='Continue']"
    val passwordInput = "id:login_password"
    val loginButton = "xpath://button[text()='Log In']"

    fun login(userNameVal: String) {

        applib.enterText(driver, userNameInput, userNameVal)
        applib.clickMobileElement(driver, continueButton)
        applib.enterText(driver, passwordInput, "strange!")
        applib.clickMobileElement(driver, loginButton)

    }
}