package be.howest.jarnelosschaert.delivermedriver.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jarnelosschaert.delivermedriver.logic.controllers.AuthController
import be.howest.jarnelosschaert.delivermedriver.logic.controllers.AppController
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.roundedBottomNav
import be.howest.jarnelosschaert.delivermedriver.ui.screens.NotificationsScreen
import be.howest.jarnelosschaert.delivermedriver.ui.screens.DeliveryDetailsScreen
import be.howest.jarnelosschaert.delivermedriver.ui.screens.HomeScreen
import be.howest.jarnelosschaert.delivermedriver.ui.screens.SettingScreen
import be.howest.jarnelosschaert.delivermedriver.ui.screens.settingScreens.ProfileScreen

sealed class BottomNavigationScreens(val route: String, val icon: ImageVector) {
    object Home : BottomNavigationScreens("home", Icons.Filled.Home)
    object Notifications : BottomNavigationScreens("notifications", Icons.Filled.Notifications)
    object Settings : BottomNavigationScreens("settings", Icons.Filled.Settings)
}

sealed class OtherScreens(val route: String) {
    object Profile : OtherScreens("profile")
    object DeliveryDetails : OtherScreens("deliveryDetails")
}

@Composable
fun DeliverMeApp(authController: AuthController) {
    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.Notifications,
        BottomNavigationScreens.Settings,
    )

    var pageClicked by remember { mutableStateOf(BottomNavigationScreens.Home.route) }

    Scaffold(
        bottomBar = {
            DeliverMeBottomNavigation(navController, bottomNavigationItems, pageClicked)
        },
        content = { innerPadding ->
            AuthScreenNavigationConfigurations(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = 15.dp, end = 8.dp),
                navController = navController,
                authController = authController,
                onNavigation = { pageClicked = it }
            )
        }
    )
}

@Composable
private fun AuthScreenNavigationConfigurations(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authController: AuthController,
    onNavigation: (String) -> Unit
) {
    val controller = AppController(navController)

    NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
        composable(BottomNavigationScreens.Home.route) {
            HomeScreen(modifier = modifier, sort = controller.uiState.sort, showDetails = { controller.navigateTo(OtherScreens.DeliveryDetails.route) }, onSortChange = { controller.onSortChange(it) })
            onNavigation(BottomNavigationScreens.Home.route)
        }
        composable(BottomNavigationScreens.Notifications.route) {
            NotificationsScreen(modifier = modifier,
                showPackageDetails = { controller.navigateTo(OtherScreens.DeliveryDetails.route) }
            )
            onNavigation(BottomNavigationScreens.Notifications.route)
        }
        composable(BottomNavigationScreens.Settings.route) {
            SettingScreen(modifier = modifier,
                navigateTo = { controller.navigateTo(it) },
            )
            onNavigation(BottomNavigationScreens.Settings.route)
        }
        composable(OtherScreens.Profile.route) {
            ProfileScreen(
                modifier = modifier,
                driver = authController.uiState.driver,
                onGoBack = { controller.goBack() },
                logout = { authController.logout() },
                deleteAccount = { authController.deleteDriver() },
                changeUserName = { authController.updateDriver(name = it) },
                changeEmail = { authController.updateDriver(email = it) },
                changePhone = { authController.updateDriver(phone = it) },
                changeWalletAddress = { authController.updateDriver(walletAddress = it) },
                changePassword = { authController.changePassword(it) },
            )
        }
        composable(OtherScreens.DeliveryDetails.route) {
            DeliveryDetailsScreen(modifier = modifier,
                onGoBack = { controller.goBack() }
            )
        }
    }
}

@Composable
fun DeliverMeBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>,
    pageClicked: String
) {

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = Modifier.clip(roundedBottomNav()),
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            var color = Color.White
            if (pageClicked == screen.route) {
                color = MaterialTheme.colors.primary
            }
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null, tint = color, modifier = Modifier.size(36.dp)) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString("")
}