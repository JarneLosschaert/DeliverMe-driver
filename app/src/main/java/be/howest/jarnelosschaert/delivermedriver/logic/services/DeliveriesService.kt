package be.howest.jarnelosschaert.delivermedriver.logic.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.logic.models.Package
import be.howest.jarnelosschaert.delivermedriver.logic.models.PackageSize
import be.howest.jarnelosschaert.delivermedriver.logic.services.other.RetrofitInstance
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
}