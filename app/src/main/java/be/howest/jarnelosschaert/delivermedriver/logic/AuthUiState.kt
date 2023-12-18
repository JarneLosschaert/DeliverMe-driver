package be.howest.jarnelosschaert.delivermedriver.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.jarnelosschaert.delivermedriver.logic.data.defaultCustomer
import be.howest.jarnelosschaert.delivermedriver.logic.data.defaultDriver

class AuthUiState : ViewModel() {
    var jwt by mutableStateOf("")
    var driver by  mutableStateOf(defaultDriver)

    var loginErrors by mutableStateOf(emptyList<String>())
    var signUpErrors by mutableStateOf(emptyList<String>())
}