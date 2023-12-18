package be.howest.jarnelosschaert.delivermedriver.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UiState : ViewModel() {
    var sort by mutableStateOf("route_asc")
}