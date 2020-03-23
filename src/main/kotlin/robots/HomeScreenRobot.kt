package robots

import com.upwork.automation.AppLibrary
import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement

fun PlatformRobot.onHomeScreen(block: HomeScreenRobot.() -> Unit) {
    val robot = HomeScreenRobot(appLibrary)
    robot.block()
}

class HomeScreenRobot(
    val appLibrary: AppLibrary,
    val driver: AppiumDriver<MobileElement> = appLibrary.driver
) {

    val userNameInput = "id:login_username"
    val continueButton = "xpath://button[text()='Continue']"
    val passwordInput = "id:login_password"
    val loginButton = "xpath://button[text()='Log In']"

    fun logout() {

    }

}