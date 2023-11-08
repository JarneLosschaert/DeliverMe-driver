package be.howest.jarnelosschaert.delivermedriver.ui.screens.settingScreens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.GeneralChoicePopup
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.GeneralTextPopup
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Content
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Label
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.SmallButton
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Title

data class PopupContent(
    val title: String,
    val label: String = "",
    val content: String = "",
    val confirmButton: String = "Change",
    val toastText: String,
    val onDismiss: () -> Unit,
    val onConfirm: () -> Unit
)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    navigateAddress: () -> Unit,
    logout: () -> Unit,
    deleteAccount: () -> Unit
) {
    var isTextPopupVisible by remember { mutableStateOf(false) }
    var textPopupContent by remember { mutableStateOf(PopupContent("", "", "", "", "", {}, {})) }
    if (isTextPopupVisible) {
        GeneralTextPopup(
            title = textPopupContent.title,
            label = textPopupContent.label,
            confirmButton = textPopupContent.confirmButton,
            toastText = textPopupContent.toastText,
            onDismiss = textPopupContent.onDismiss,
            onConfirm = { isTextPopupVisible = false }
        )
    }
    var isChoicePopupVisible by remember { mutableStateOf(false) }
    var choicePopupContent by remember { mutableStateOf(PopupContent("", "", "", "", "", {}, {})) }
    if (isChoicePopupVisible) {
        GeneralChoicePopup(
            title = choicePopupContent.title,
            content = choicePopupContent.content,
            confirmButton = choicePopupContent.confirmButton,
            toastText = choicePopupContent.toastText,
            onDismiss = choicePopupContent.onDismiss,
            onConfirm = choicePopupContent.onConfirm
        )
    }
    Box(modifier = modifier.fillMaxWidth()) {
        Column() {
            Title(text = "Profile", onGoBack = onGoBack, withGoBack = true)
            LazyColumn(content = {
                item {
                    ProfilePicture()
                    Profile(
                        onTextEdit = { textPopupContent = it; isTextPopupVisible = true },
                        onChoiceEdit = { choicePopupContent = it; isChoicePopupVisible = true },
                        onDismiss = { isTextPopupVisible = false; isChoicePopupVisible = false },
                        navigateAddress = navigateAddress,
                    )
                    ProfileButtons(
                        logout = logout,
                        deleteAccount = deleteAccount,
                        navigateAddress = navigateAddress,
                        onEdit = { choicePopupContent = it; isChoicePopupVisible = true },
                        onDismiss = { isChoicePopupVisible = false }
                    )
                }
            })
        }
    }

}

@Composable
fun ProfileButtons(
    logout: () -> Unit,
    deleteAccount: () -> Unit,
    onEdit: (PopupContent) -> Unit,
    onDismiss: () -> Unit,
    navigateAddress: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        SmallButton(text = "Add address", onClick = navigateAddress)
        SmallButton(text = "Change password", onClick = {})
        SmallButton(text = "Change profile picture", onClick = {})
        SmallButton(
            text = "Log out",
            isError = true,
            modifier = Modifier.align(Alignment.End),
            onClick = {
                onEdit(
                    PopupContent(
                        title = "Log out",
                        content = "Are you sure you want to log out?",
                        toastText = "Logged out",
                        confirmButton = "Log out",
                        onDismiss = { onDismiss() },
                        onConfirm = { logout() }
                    )
                )
            }
        )
        SmallButton(
            text = "Delete account",
            isError = true,
            modifier = Modifier.align(Alignment.End),
            onClick = {
                onEdit(
                    PopupContent(
                        title = "Delete account",
                        content = "Are you sure you want to delete your account?",
                        confirmButton = "Delete",
                        toastText = "Account deleted",
                        onDismiss = { onDismiss() },
                        onConfirm = { deleteAccount() }
                    )
                )
            }
        )
    }
}

@Composable
fun Profile(
    onTextEdit: (PopupContent) -> Unit,
    onChoiceEdit: (PopupContent) -> Unit,
    onDismiss: () -> Unit,
    navigateAddress: () -> Unit
) {
    EditableContentLabel(label = "Username",
        text = "Daan Hautekiet",
        onEdit = onTextEdit,
        popupContent = PopupContent(
            title = "Change username",
            label = "New username",
            toastText = "Username changed",
            onDismiss = { onDismiss() },
            onConfirm = {}
        ))
    EditableContentLabel(label = "Email",
        text = "daan.hautekiet@gmail.com",
        onEdit = onTextEdit,
        popupContent = PopupContent(
            title = "Change email",
            label = "New email",
            toastText = "Email changed",
            onDismiss = { onDismiss() },
            onConfirm = {}
        ))
    EditableContentLabel(label = "Phone number",
        text = "0472 12 34 56",
        onEdit = onTextEdit,
        popupContent = PopupContent(
            title = "Change phone number",
            label = "New phone number",
            toastText = "Phone number changed",
            onDismiss = { onDismiss() },
            onConfirm = {}
        ))
    EditableContentLabel(label = "Home address",
        text = "Kortrijksestraat 12, 8500 Kortrijk",
        onEdit = onChoiceEdit,
        popupContent = PopupContent(
            title = "Change home address",
            content = "Are you sure you want to change your home address?",
            toastText = "Changing home address",
            onDismiss = { onDismiss() },
            onConfirm = { navigateAddress() }
        ))
    EditableContentLabel(label = "Extra address 1",
        text = "Kortrijksestraat 12, 8500 Kortrijk",
        onEdit = onChoiceEdit,
        popupContent = PopupContent(
            title = "Change work address",
            content = "Are you sure you want to change your work address?",
            toastText = "Changing work address",
            onDismiss = { onDismiss() },
            onConfirm = { navigateAddress() }
        ))
}

@Composable
fun EditableContentLabel(
    label: String,
    text: String,
    onEdit: (PopupContent) -> Unit,
    popupContent: PopupContent
) {
    Label(text = label)
    Box(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colors.onBackground, MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Content(text = text, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .size(20.dp)
                    .clickable(onClick = { onEdit(popupContent) })
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ProfilePicture() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Profile picture",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}
