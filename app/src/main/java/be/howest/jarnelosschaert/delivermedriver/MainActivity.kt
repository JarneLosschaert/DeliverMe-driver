package be.howest.jarnelosschaert.delivermedriver

import SocketApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.delivermedriver.ui.Authorize
import be.howest.jarnelosschaert.delivermedriver.ui.theme.DeliverMedriverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeliverMedriverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SocketApp()
                }
            }
        }
    }
}