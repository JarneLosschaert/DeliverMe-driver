package be.howest.jarnelosschaert.delivermedriver.logic.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jarnelosschaert.delivermedriver.logic.models.Customer
import be.howest.jarnelosschaert.delivermedriver.logic.models.Driver
import be.howest.jarnelosschaert.delivermedriver.logic.services.other.RetrofitInstance
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.LoginRequest
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.UpdateDriverRequest
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.UpdatePasswordRequest
import be.howest.jarnelosschaert.delivermedriver.logic.services.responses.RegistrationLoginResponse
import kotlinx.coroutines.launch

class AuthService : ViewModel() {
    private val apiService = RetrofitInstance.apiService

    fun signUp(
        username: String,
        email: String,
        phone: String,
        password: String,
        walletAddress: String,
        handleSuccess: (RegistrationLoginResponse) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val registerRequest = RegisterRequest(
                username,
                email,
                phone,
                password,
                walletAddress
            )
            try {
                val response = apiService.registerUser(registerRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to register")
                println("Error register: ${e.message}")
            }
        }
    }

    fun login(
        email: String,
        password: String,
        handleSuccess: (RegistrationLoginResponse) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val loginRequest = LoginRequest(email, password)
            try {
                val response = apiService.loginUser(loginRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to login")
                println("Error login: ${e.message}")
            }
        }
    }

    fun updateCustomer(
        jwt: String,
        id: Int,
        username: String,
        email: String,
        phone: String,
        walletAddress: String,
        handleSuccess: (Driver) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            val updateDriverRequest = UpdateDriverRequest(
                username,
                email,
                phone,
                walletAddress,
            )
            try {
                val response = apiService.updateDriver("Bearer $jwt", id, updateDriverRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to update driver")
                println("Error update driver: ${e.message}")
            }
        }
    }

    fun deleteDriver(
        jwt: String,
        id: Int,
        handleSuccess: () -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                apiService.deleteDriver("Bearer $jwt", id)
                handleSuccess()
            } catch (e: Exception) {
                handleFailure("Failed to delete driver")
                println("Error delete driver: ${e.message}")
            }
        }
    }

    fun changePassword(
        jwt: String,
        password: String,
        handleSuccess: () -> Unit,
        handleFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val updatePasswordRequest = UpdatePasswordRequest(password)
            try {
                apiService.changePassword("Bearer $jwt", updatePasswordRequest)
                handleSuccess()
            } catch (e: Exception) {
                handleFailure("Failed to change password")
                println("Error change password: ${e.message}")
            }
        }
    }
}
