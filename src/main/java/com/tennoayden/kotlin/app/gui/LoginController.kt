package com.tennoayden.kotlin.app.gui
import com.tennoayden.app.gui.controllers.HomeController
import javafx.beans.property.SimpleStringProperty

import tornadofx.*

class LoginController : Controller() {
    val statusProperty = SimpleStringProperty("")
    var status by statusProperty
   // val homeController=HomeController("test")
    val api: Rest by inject()
    val user: UserModel by inject()

    init {
        api.baseURI = "https://api.github.com/"
    }

    fun login(username: String, password: String) {
        runLater { status = "" }
        api.setBasicAuth(username, password)
        val response = api.get("user")
        val json = response.one()
        runLater {
            if (response.ok()) {
                user.item = json.toModel()
                //find(LoginScreen::class).replaceWith(WelcomeScreen::class, sizeToScene = true, centerOnScreen = true)
               val homeController=HomeController("test")
                find(LoginScreen::class).close()

            } else {
                status = json.string("message") ?: "Login failed"
            }
        }
    }

    fun logout() {
        user.item = null
        primaryStage.uiComponent<UIComponent>()?.replaceWith(LoginScreen::class, sizeToScene = true, centerOnScreen = true)
    }

}