package be.howest.jarnelosschaert.delivermedriver.logic.controllers

import android.widget.Toast
import androidx.navigation.NavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import be.howest.jarnelosschaert.delivermedriver.logic.AppUiState
import be.howest.jarnelosschaert.delivermedriver.logic.data.Sort
import be.howest.jarnelosschaert.delivermedriver.logic.data.defaultSort
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.DirectionsApi
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.LocationUpdateWorker
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.getLocation
import be.howest.jarnelosschaert.delivermedriver.logic.helpers.sortDeliveriesByDistance
import be.howest.jarnelosschaert.delivermedriver.logic.models.Address
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.logic.models.DeliveryState
import be.howest.jarnelosschaert.delivermedriver.logic.services.DeliveriesService
import be.howest.jarnelosschaert.delivermedriver.ui.BottomNavigationScreens
import be.howest.jarnelosschaert.delivermedriver.ui.OtherScreens
import com.google.gson.Gson

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
                startUpdateWorker()
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
                startUpdateWorker()
                navigateTo(BottomNavigationScreens.Home.route)
            },
            handleFailure = { message ->
                Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun startUpdateWorker() {
        val workManager = WorkManager.getInstance(navController.context)
        workManager.cancelAllWork()

        for (delivery in uiState.activeDeliveries) {
            val deliveryJson = Gson().toJson(delivery)
            val inputData = Data.Builder()
                .putString("email", authController.uiState.driver.person.email)
                .putString("delivery", deliveryJson)
                .build()

            val workRequest: WorkRequest =
                OneTimeWorkRequest.Builder(LocationUpdateWorker::class.java)
                    .setInputData(inputData)
                    .build()

            WorkManager.getInstance(navController.context).enqueue(workRequest)
        }
    }

    fun onReceivedTap() {
        val newDelivery = uiState.selectedActiveDelivery.copy(state = DeliveryState.transit)
        updateDelivery(newDelivery)
    }

    fun onDeliveredTap() {
        val newDelivery = uiState.selectedActiveDelivery.copy(state = DeliveryState.delivered)
        updateDelivery(newDelivery)
        startUpdateWorker()
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
                if (newDelivery.state == DeliveryState.delivered) {
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
                uiState.deliveries = uiState.deliveries.sortedByDescending { it.`package`.distance }
            }
            Sort.DISTANCE_ASC -> {
                uiState.deliveries = uiState.deliveries.sortedBy { it.`package`.distance }
            }
            Sort.CLOSEST -> {
                getLocation(
                    navController.context,
                    onLocationResult = { location ->
                        println(location)
                        if (location != null) {
                            uiState.deliveries =
                                sortDeliveriesByDistance(location, uiState.deliveries)
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