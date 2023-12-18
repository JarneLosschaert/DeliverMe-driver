package be.howest.jarnelosschaert.delivermedriver.ui.screens.authScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.delivermedriver.logic.data.SignUp
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.*

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    errors: List<String>,
    navigateToLogin: () -> Unit,
    signUp: (SignUp) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var walletAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Title()
            LazyColumn(
                content = {
                    item {
                        SubTitle(text = "Create your account", modifier = Modifier.padding(top = 20.dp))
                        TextFieldLabel(label = "Username", value = username, onValueChange = { username = it })
                        TextFieldLabel(label = "Email", value = email, onValueChange = { email = it }, isEmail = true)
                        TextFieldLabel(label = "Phone number", value = phone, onValueChange = { phone = it}, isNumber = true)
                        TextFieldLabel(label = "Card number", value = walletAddress, onValueChange = { walletAddress = it})
                        TextFieldLabel(label = "Password", value = password, onValueChange = { password = it }, isPassword = true)
                        TextFieldLabel(label = "Confirm password", value = confirmPassword, onValueChange = { confirmPassword = it }, isPassword = true)
                        Errors(errors = errors)
                        GeneralButton(
                            text = "Sign up",
                            onClick = { signUp(SignUp(username, email, phone, walletAddress, password, confirmPassword)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                        AuthBottomNavigate(navigate = navigateToLogin, label = "Already have an account?", text = "Log in")
                    }
                }
            )
        }
    }

}