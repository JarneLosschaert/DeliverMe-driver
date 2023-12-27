package be.howest.jarnelosschaert.delivermedriver.ui.helpers.functions

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun HandleLocationPermissions(onPermission: (Boolean) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermission(true)
        } else {
            onPermission(false)
        }
    }

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == android.content.pm.PackageManager.PERMISSION_GRANTED
    ) {
        onPermission(true)
    } else {
        LaunchedEffect(true) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}
