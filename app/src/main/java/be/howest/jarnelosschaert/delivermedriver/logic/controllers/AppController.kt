package be.howest.jarnelosschaert.delivermedriver.logic.controllers

import android.widget.Toast
import androidx.navigation.NavController
import be.howest.jarnelosschaert.delivermedriver.logic.AppUiState
import be.howest.jarnelosschaert.delivermedriver.logic.services.AuthService
import be.howest.jarnelosschaert.delivermedriver.logic.services.DeliveriesService
import be.howest.jarnelosschaert.delivermedriver.ui.BottomNavigationScreens

class AppController(
    private val navController: NavController,
    private val authController: AuthController
) {
    val uiState: AppUiState = AppUiState()
    private val authService = AuthService()
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
                Toast.makeText(navController.context, message, Toast.LENGTH_SHORT).show()
                uiState.refreshing = false
            }
        )
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

    fun onSortChange(sort: String) {
        uiState.sort = sort
    }
}