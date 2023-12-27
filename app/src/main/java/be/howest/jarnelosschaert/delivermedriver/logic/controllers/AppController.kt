package be.howest.jarnelosschaert.delivermedriver.logic.controllers

import android.widget.Toast
import androidx.navigation.NavController
import be.howest.jarnelosschaert.delivermedriver.logic.AppUiState
import be.howest.jarnelosschaert.delivermedriver.logic.data.Sort
import be.howest.jarnelosschaert.delivermedriver.logic.data.defaultSort
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.DirectionsApi
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.getLocation
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.sortDeliveriesByDistance
import be.howest.jarnelosschaert.delivermedriver.logic.models.Address
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
        loadActiveDeliveries()
        loadDeliveries()
    }

    fun loadDeliveries(refreshing: Boolean = false) {
        if (refreshing) uiState.refreshing = true
        deliveriesService.getDeliveries(
            authController.uiState.jwt,
            handleSuccess = { deliveries ->
                uiState.deliveries = deliveries
                onSortChange()
                uiState.refreshing = false
                if (refreshing) {
                    Toast.makeText(
                        navController.context,
                        "Deliveries up to date",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            handleFailure = {
                uiState.refreshing = false
            }
        )
    }

    fun loadActiveDeliveries(refreshing: Boolean = false) {
        if (refreshing) uiState.refreshing = true
        deliveriesService.getActiveDeliveries(
            authController.uiState.jwt,
            handleSuccess = { deliveries ->
                uiState.activeDeliveries = deliveries
                uiState.refreshing = false
                if (refreshing) {
                    Toast.makeText(
                        navController.context,
                        "Active deliveries up to date",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            handleFailure = {
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
                uiState.selectedActiveDelivery = it
                navigateTo(BottomNavigationScreens.Home.route)
            },
            handleFailure = { message ->
                Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun onReceivedTap() {
        val newDelivery = uiState.selectedActiveDelivery.copy(state = DeliveryState.TRANSIT)
        updateDelivery(newDelivery)
    }

    fun onDeliveredTap() {
        val newDelivery = uiState.selectedActiveDelivery.copy(state = DeliveryState.DELIVERED)
        updateDelivery(newDelivery)
    }

    private fun updateDelivery(delivery: Delivery) {
        deliveriesService.updateDelivery(
            authController.uiState.jwt,
            delivery.id,
            delivery.state,
            handleSuccess = { newDelivery ->
                Toast.makeText(navController.context, "Delivery updated", Toast.LENGTH_SHORT)
                    .show()
                uiState.selectedActiveDelivery = newDelivery
                if (newDelivery.state == DeliveryState.DELIVERED) {
                    navigateTo(BottomNavigationScreens.Home.route)
                }

            },
            handleFailure = { message ->
                Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun onDeliveryTap(delivery: Delivery) {
        uiState.selectedDelivery = delivery
        navController.navigate(OtherScreens.DeliveryDetails.route)
    }

    fun onActiveDeliveryTap(delivery: Delivery) {
        uiState.selectedActiveDelivery = delivery
        navController.navigate(OtherScreens.ActiveDelivery.route)
    }

    fun onSortChange(sort: Sort = defaultSort) {
        when (sort) {
            Sort.DISTANCE_DESC -> {
                uiState.deliveries = uiState.deliveries.sortedByDescending { it.packageInfo.distance }
            }
            Sort.DISTANCE_ASC -> {
                uiState.deliveries = uiState.deliveries.sortedBy { it.packageInfo.distance }
            }
            Sort.CLOSEST -> {
                getLocation(
                    navController.context,
                    onLocationResult = { location ->
                        println(location)
                        if (location != null) {
                            uiState.deliveries = sortDeliveriesByDistance(location, uiState.deliveries)
                        }
                    }
                )
            }
        }
        uiState.sort = sort
    }
    fun navigateAddress(address: Address) {
        DirectionsApi.openGoogleMapsNavigationToB(navController.context, address.lat, address.lon)
    }

    fun goBack() {
        if (navController.currentDestination?.route == BottomNavigationScreens.Home.route) {
            //uiState.showExitDialog = true
        } else {
            navController.popBackStack()
        }
    }

    fun navigateTo(route: String) {
        if (route == BottomNavigationScreens.Home.route) {
            loadActiveDeliveries()
        } else if (route == BottomNavigationScreens.Deliveries.route) {
            loadDeliveries()
        }
        navController.navigate(route)
    }
}