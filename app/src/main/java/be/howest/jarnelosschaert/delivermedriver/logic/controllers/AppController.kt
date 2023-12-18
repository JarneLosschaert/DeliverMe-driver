package be.howest.jarnelosschaert.delivermedriver.logic.controllers

import androidx.navigation.NavController
import be.howest.jarnelosschaert.delivermedriver.logic.UiState
import be.howest.jarnelosschaert.delivermedriver.ui.BottomNavigationScreens

class AppController(private val navController: NavController) {
    val uiState: UiState = UiState()

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