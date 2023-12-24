package be.howest.jarnelosschaert.delivermedriver.logic.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.logic.models.DeliveryState
import be.howest.jarnelosschaert.delivermedriver.logic.services.other.RetrofitInstance
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.UpdateDeliveryRequest
import kotlinx.coroutines.launch

class DeliveriesService: ViewModel() {
    private val apiService = RetrofitInstance.apiService

    fun getDeliveries(
        jwt: String,
        handleSuccess: (List<Delivery>) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.getDeliveries("Bearer $jwt")
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to get deliveries")
                println("Error get deliveries: ${e.message}")
            }
        }
    }

    fun assignDelivery(
        jwt: String,
        id: Int,
        handleSuccess: (Delivery) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val response = apiService.assignDelivery("Bearer $jwt", id)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to assign delivery")
                println("Error assign delivery: ${e.message}")
            }
        }
    }

    fun updateDelivery(
        jwt: String,
        id: Int,
        state: DeliveryState,
        handleSuccess: (Delivery) -> Unit,
        handleFailure: (String) -> Unit,
    ) {
        val updateDeliveryRequest = UpdateDeliveryRequest(state)
        viewModelScope.launch {
            try {
                val response = apiService.updateDelivery("Bearer $jwt", id, updateDeliveryRequest)
                handleSuccess(response)
            } catch (e: Exception) {
                handleFailure("Failed to update delivery")
                println("Error update delivery: ${e.message}")
            }
        }
    }
}