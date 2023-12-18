package be.howest.jarnelosschaert.delivermedriver.logic.controllers

import android.widget.Toast
import androidx.navigation.NavController
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.delivermedriver.logic.AuthUiState
import be.howest.jarnelosschaert.delivermedriver.logic.data.SignUp
import be.howest.jarnelosschaert.delivermedriver.logic.data.defaultDriver
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.checkValuesSignUp
import be.howest.jarnelosschaert.delivermedriver.logic.services.AuthService
import be.howest.jarnelosschaert.delivermedriver.logic.services.responses.RegistrationLoginResponse
import be.howest.jarnelosschaert.delivermedriver.ui.AuthorizeScreens

class AuthController(
    private val navController: NavController
) {
    private val authService = AuthService()
    val uiState = AuthUiState()

    private var _signUp: SignUp = SignUp("", "", "", "", "", "")

    fun login(email: String, password: String) {
        clearErrors()
        authService.login(
            email,
            password,
            { handleLoginSignUpSuccess(it) },
            { handleLoginFailure(it) })
    }

    fun updateDriver(
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        walletAddress: String? = null
    ) {
        val updatedPerson = uiState.driver.person.copy(
            name = name ?: uiState.driver.person.name,
            email = email ?: uiState.driver.person.email,
            phone = phone ?: uiState.driver.person.phone
        )
        val updatedDriver = uiState.driver.copy(
            person = updatedPerson,
            walletAddress = walletAddress ?: uiState.driver.walletAddress
        )
        authService.updateDriver(
            uiState.jwt,
            updatedDriver.id,
            updatedDriver.person.name,
            updatedDriver.person.email,
            updatedDriver.person.phone,
            updatedDriver.walletAddress,
            handleSuccess = {
                uiState.driver = updatedDriver
                Toast.makeText(navController.context, "Account updated", Toast.LENGTH_SHORT).show()
            },
            handleFailure = {
                Toast.makeText(navController.context, "Account update failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun changePassword(password: String) {
        authService.changePassword(
            uiState.jwt,
            password,
            handleSuccess = {
                Toast.makeText(navController.context, "Password changed", Toast.LENGTH_SHORT).show()
            },
            handleFailure = {
                Toast.makeText(navController.context, "Password change failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun logout() {
        navController.navigate(AuthorizeScreens.Login.route)
        uiState.jwt = ""
        uiState.driver = defaultDriver
        Toast.makeText(navController.context, "Logged out", Toast.LENGTH_SHORT).show()
    }

    fun signUp(signUp: SignUp) {
        clearErrors()
        uiState.signUpErrors = checkValuesSignUp(
            signUp.username,
            signUp.email,
            signUp.phone,
            signUp.walletAddress,
            signUp.password,
            signUp.confirmPassword
        )
        if (uiState.signUpErrors.isEmpty()) {
            _signUp = signUp
            authService.signUp(
                _signUp.username,
                _signUp.email,
                _signUp.phone,
                _signUp.password,
                _signUp.walletAddress,
                { handleLoginSignUpSuccess(it) },
                { handleSignUpFailure(it) }
            )
        }

    }

    fun deleteDriver() {
        authService.deleteDriver(uiState.jwt, uiState.driver.id,
            handleSuccess = {
                logout()
                Toast.makeText(navController.context, "Account deleted", Toast.LENGTH_SHORT).show()
            },
            handleFailure = {
                Toast.makeText(navController.context, "Failed", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun handleLoginSignUpSuccess(response: RegistrationLoginResponse) {
        uiState.jwt = response.jwt
        uiState.driver = response.driver
        navController.navigate(AuthorizeScreens.App.route)
        clearErrors()
    }

    private fun handleLoginFailure(error: String) {
        uiState.loginErrors = listOf(error)
    }

    private fun handleSignUpFailure(error: String) {
        uiState.signUpErrors = listOf(error)
    }

    private fun clearErrors() {
        uiState.loginErrors = emptyList()
        uiState.signUpErrors = emptyList()
    }
}