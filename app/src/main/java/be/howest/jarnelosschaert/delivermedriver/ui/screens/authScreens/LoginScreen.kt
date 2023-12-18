package be.howest.jarnelosschaert.delivermedriver.ui.screens.authScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.*

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    errors: List<String>,
    navigateToSignUp: () -> Unit,
    login: (String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            content = {
                item {
                    Title()
                    SubTitle(text = "Login details", modifier = Modifier.padding(top = 20.dp))
                    TextFieldLabel(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        isEmail = true
                    )
                    TextFieldLabel(
                        label = "Password",
                        value = password,
                        onValueChange = { password = it },
                        isPassword = true
                    )
                    Errors(errors = errors)
                    GeneralButton(
                        text = "Log in",
                        onClick = { login(email, password) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    AuthBottomNavigate(
                        navigate = navigateToSignUp,
                        label = "Don't have an account?",
                        text = "Sign up"
                    )
                }
            }
        )
    }
}