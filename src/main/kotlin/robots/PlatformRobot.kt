package robots

import com.upwork.automation.AppLibrary

fun platform(block: PlatformRobot.() -> Unit) {
    val robot = PlatformRobot()
    robot.block()
}

class PlatformRobot : Robot {
    override var appLibrary: AppLibrary = AppLibrary()

    fun launchApp(): AppLibrary {
        appLibrary.launchDriver()
        return appLibrary
    }

}