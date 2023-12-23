package be.howest.jarnelosschaert.delivermedriver.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import be.howest.jarnelosschaert.delivermedriver.logic.data.defaultDelivery
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery

class AppUiState : ViewModel() {
    var deliveries by mutableStateOf(listOf<Delivery>())
    var sort by mutableStateOf("route_asc")
    var refreshing by mutableStateOf(false)

    var selectedDelivery: Delivery by mutableStateOf(defaultDelivery)

    var activeDelivery: Delivery? by mutableStateOf(null)
}