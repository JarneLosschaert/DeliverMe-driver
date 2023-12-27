package be.howest.jarnelosschaert.delivermedriver.ui.helpers.components

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

@Composable
fun FingerPrintButton(
    text: String,
    onSuccess: () -> Unit,
) {
    val context = LocalContext.current
    val biometricManager = remember { BiometricManager.from(context) }

    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
    }
    when (isBiometricAvailable) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            val executor = remember { ContextCompat.getMainExecutor(context) }
            val biometricPrompt = BiometricPrompt(
                context as FragmentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        println("Authentication error: $errString")
                        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }

                    @RequiresApi(Build.VERSION_CODES.R)
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        onSuccess()
                        Toast.makeText(context, "Authentication succeeded", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setAllowedAuthenticators(BIOMETRIC_STRONG)
                .setTitle("Biometric Authentication")
                .setSubtitle("Check if it's you!")
                .setNegativeButtonText("Use account password")
                .build()

            GeneralButton(
                onTap = { biometricPrompt.authenticate(promptInfo) },
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
