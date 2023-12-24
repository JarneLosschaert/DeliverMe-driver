package be.howest.jarnelosschaert.delivermedriver.logic.controllers

import android.widget.Toast
import androidx.navigation.NavController
import be.howest.jarnelosschaert.delivermedriver.logic.AppUiState
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.DirectionsApi
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.logic.models.DeliveryState
import be.howest.jarnelosschaert.delivermedriver.logic.services.DeliveriesService
import be.howest.jarnelosschaert.delivermedriver.ui.BottomNavigationScreens
import be.howest.jarnelosschaert.delivermedriver.ui.OtherScreens

class AppController(
    private val navController: NavController,
    private val authController: AuthController
) {
    val uiState: AppUiState = AppUiState()
    private val deliveriesService = DeliveriesService()

    init {
        loadDeliveries()
    }

    fun loadDeliveries(refreshing: Boolean = false) {
        if (refreshing) uiState.refreshing = true
        deliveriesService.getDeliveries(
            authController.uiState.jwt,
            handleSuccess = { deliveries ->
                uiState.deliveries = deliveries
                uiState.refreshing = false
                if (refreshing) {
                    Toast.makeText(
                        navController.context,
                        "Deliveries up to date",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                println(deliveries[0].id)
            },
            handleFailure = { message ->
                println(message)
                uiState.refreshing = false
            }
        )
    }

    fun onAssignTap() {
        deliveriesService.assignDelivery(
            authController.uiState.jwt,
            uiState.selectedDelivery.id,
            handleSuccess = {
                Toast.makeText(navController.context, "Delivery assigned", Toast.LENGTH_SHORT)
                    .show()
                uiState.activeDelivery = it
                navigateTo(BottomNavigationScreens.Home.route)
            },
            handleFailure = { message ->
                Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
            }
        )
    }
    fun onReceivedTap() {
        updateDelivery(uiState.activeDelivery?.copy(state = DeliveryState.TRANSIT))
    }
    fun onDeliveredTap() {
        updateDelivery(uiState.activeDelivery?.copy(state = DeliveryState.DELIVERED))
    }
    private fun updateDelivery(delivery: Delivery?) {
        delivery?.let {
            deliveriesService.updateDelivery(
                authController.uiState.jwt,
                delivery.id,
                delivery.state,
                handleSuccess = { newDelivery ->
                    Toast.makeText(navController.context, "Delivery delivered", Toast.LENGTH_SHORT)
                        .show()
                    uiState.activeDelivery = newDelivery
                },
                handleFailure = { message ->
                    Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
    fun onDeliveryTap(delivery: Delivery) {
        uiState.selectedDelivery = delivery
        navController.navigate(OtherScreens.DeliveryDetails.route)
    }
    fun onSortChange(sort: String) {
        uiState.sort = sort
    }
    fun navigateAddress() {
        DirectionsApi.openGoogleMapsNavigationToB(navController.context, 51.19, 3.18)
    }
    fun goBack() {
        if (navController.currentDestination?.route == BottomNavigationScreens.Home.route) {
            //uiState.showExitDialog = true
        } else {
            navController.popBackStack()
        }
    }
    fun navigateTo(route: String) {
        navController.navigate(route)
    }
}