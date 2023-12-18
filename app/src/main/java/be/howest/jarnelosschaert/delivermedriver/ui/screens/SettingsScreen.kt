package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralPopup
import be.howest.jarnelosschaert.deliverme.ui.helpers.components.GeneralTextPopup
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Title

sealed class Setting {
    abstract val setting: String
    abstract val icon: ImageVector
}
sealed class SettingsPage(override val setting: String, override val icon: ImageVector, val route: String): Setting() {
    object Account : SettingsPage("Profile", Icons.Default.AccountCircle, "profile")
}

sealed class SettingsPopup(override val setting: String, override val icon: ImageVector, val popup: @Composable (() -> Unit) -> Unit): Setting() {
    object Nothing : SettingsPopup("", Icons.Default.Lock, {})
    object Help : SettingsPopup("Help", Icons.Default.Call, { onDismiss ->
        GeneralTextPopup(
            title = "Help",
            label = "Do you need help with the app? Let us know!",
            confirmButton = "Send",
            onDismiss = { onDismiss() }
        )
    })
    object Problem : SettingsPopup("Problem", Icons.Default.Warning, { onDismiss ->
        GeneralTextPopup(
            title = "Problem",
            label = "Do you have a problem with the app? Let us know!",
            confirmButton = "Send",
            onDismiss = { onDismiss() }
        )
    })
    object Info : SettingsPopup("Info", Icons.Default.Info, { onDismiss ->
        GeneralPopup(
            title = "Info",
            content = "Package delivery app for the driver",
            confirmButton = "Ok",
            onDismiss = { onDismiss() },
        )
    })
}


@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navigateTo: (String) -> Unit,
) {

    val settings = listOf(
        SettingsPage.Account,
        SettingsPopup.Help,
        SettingsPopup.Problem,
        SettingsPopup.Info
    )
    Box(modifier = modifier.fillMaxWidth()) {
        LazyColumn(content = {
            item {
                Title(text = "Settings")
            }
            item {
                Column {
                    settings.forEach { setting ->
                        SettingItem(setting = setting, navigateTo = navigateTo)
                    }
                }
            }
        })
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    setting: Setting,
    navigateTo: (String) -> Unit
) {
    var displayPopup by remember { mutableStateOf<SettingsPopup>(SettingsPopup.Nothing) }
    if (displayPopup !is SettingsPopup.Nothing) {
        displayPopup.popup { displayPopup = SettingsPopup.Nothing }
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {
                if (setting is SettingsPage) {
                    navigateTo(setting.route)
                }
                if (setting is SettingsPopup) {
                    displayPopup = setting
                }
            })
            .padding(end = 16.dp),
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = setting.icon, contentDescription = null, tint = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = setting.setting, modifier = Modifier.weight(1f), fontSize = MaterialTheme.typography.h6.fontSize, color = MaterialTheme.colors.primary)
                if (setting is SettingsPage) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colors.primary)
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = MaterialTheme.colors.primary
            )
        }

    }
}