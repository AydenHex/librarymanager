package com.tennoayden.kotlin.app.gui

import javafx.geometry.Pos
import javafx.scene.text.FontWeight

import tornadofx.*

class WelcomeScreen : View("Welcome") {
    val user: UserModel by inject()
    val loginController: LoginController by inject()

    override val root = vbox(10) {
        setPrefSize(800.0, 600.0)
        alignment = Pos.CENTER

        label(user.name) {
            style {
                fontWeight = FontWeight.BOLD
                fontSize = 24.px
            }
        }

        button("Logout").action(loginController::logout)

    }
}