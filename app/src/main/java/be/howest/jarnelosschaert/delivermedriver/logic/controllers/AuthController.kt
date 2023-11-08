package be.howest.jarnelosschaert.delivermedriver.logic.controllers

import androidx.navigation.NavController
import be.howest.jarnelosschaert.delivermedriver.logic.models.SignUp
import be.howest.jarnelosschaert.delivermedriver.ui.AuthorizeScreens

class AuthController(
    private val navController: NavController
) {
    private var isLoggedIn: Boolean = false

    fun login() {
        isLoggedIn = true
        navController.navigate(AuthorizeScreens.App.route)
    }

    fun logout() {
        isLoggedIn = false
        navController.navigate(AuthorizeScreens.Login.route)
    }

    fun signUp(signUp: SignUp) : List<String> {
        val errors = checkValuesSignUp(signUp.username, signUp.email, signUp.phone, signUp.password, signUp.confirmPassword)
        if (errors.isEmpty()) {
            login()
        }
        return errors
    }

    fun deleteAccount() {
        logout()
    }

    fun isLoggedIn(): Boolean {
        return isLoggedIn
    }

    private fun checkValuesSignUp(
        username: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): List<String> {
        val errors = mutableListOf<String>()

        if (username.isBlank()) {
            errors.add("Username is required.")
        }

        if (email.isBlank()) {
            errors.add("Email is required.")
        } else if (!isValidEmail(email)) {
            errors.add("Invalid email format.")
        }

        if (phone.isBlank()) {
            errors.add("Phone number is required.")
        } else if (!isValidPhoneNumber(phone)) {
            errors.add("Invalid phone number format.")
        }

        if (password.isBlank()) {
            errors.add("Password is required.")
        } else if (password.length < 8) {
            errors.add("Password must be at least 8 characters long.")
        } else if (password != confirmPassword) {
            errors.add("Passwords do not match.")
        }

        return errors
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
        return email.matches(emailRegex.toRegex())
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        val cleanedPhoneNumber = phone.replace(Regex("\\D"), "")
        return cleanedPhoneNumber.startsWith("0") && cleanedPhoneNumber.length == 10
    }
}