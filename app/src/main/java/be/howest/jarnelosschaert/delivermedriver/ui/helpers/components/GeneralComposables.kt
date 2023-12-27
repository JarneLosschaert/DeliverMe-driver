package be.howest.jarnelosschaert.delivermedriver.ui.helpers.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Title(text: String = "DeliverMe", onGoBack: () -> Unit = {}, withGoBack: Boolean = false) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (withGoBack) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onGoBack)
            ) {
                Text(
                    text = "<",
                    color = MaterialTheme.colors.primary,
                    fontSize = 35.sp,
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Text(
            text = text,
            color = MaterialTheme.colors.primary,
            fontSize = 35.sp,
            modifier = Modifier
                .padding(15.dp)
                .align(Alignment.Center),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun SubTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier.padding(top = 10.dp, bottom = 10.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}

@Composable
fun Label(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onSecondary,
        fontSize = 15.sp,
        modifier = Modifier.padding(0.dp)
    )
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onBackground,
    fontSize: Int = 17,
    text: String,
    isError: Boolean = false
) {
    Text(
        text = text,
        color = if (isError) MaterialTheme.colors.primaryVariant else color,
        fontSize = fontSize.sp,
        modifier = modifier,
        fontWeight = if (isError) FontWeight.Bold else FontWeight.Normal,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun DateDetails(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        fontSize = 15.sp,
        textAlign = TextAlign.End,
        modifier = Modifier.fillMaxWidth(),
        fontStyle = FontStyle.Italic
    )
}
@Composable
fun ContentLabel(label: String, content: String) {
    Label(text = label)
    Content(text = content)
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun GeneralButton(
    modifier: Modifier = Modifier,
    text: String = "See details",
    onTap: () -> Unit,
    isError: Boolean = false
) {
    var color = MaterialTheme.colors.primary
    if (isError) color = MaterialTheme.colors.primaryVariant
    Button(
        onClick = onTap,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(text = text, fontSize = 15.sp)
    }
}

@Composable
fun roundedBottomNav() = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

@Composable
fun TextFieldLabel(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    isNumber: Boolean = false
) {
    Label(text = label)
    GeneralTextField(
        text = value,
        onValueChange = onValueChange,
        isPassword = isPassword,
        isEmail = isEmail,
        isPhone = isNumber
    )
    Spacer(modifier = Modifier.padding(top = 10.dp))
}

@Composable
fun GeneralTextField(
    text: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    isPhone: Boolean = false,
    placeholder: String = ""
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
    ) {
        TextField(
            value = text,
            onValueChange = { onValueChange(it) },
            textStyle = TextStyle(
                fontSize = 18.sp
            ),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isEmail) KeyboardType.Email else if (isPhone) KeyboardType.Phone else KeyboardType.Text
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            },
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun Errors(errors: List<String>) {
    errors.forEach {
        Content(text = it, isError = true)
    }
}

@Composable
fun AuthBottomNavigate(
    label: String,
    text: String,
    navigate: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Label(text = label)
        GeneralButton(text = text, onTap = navigate, modifier = Modifier.padding(start = 30.dp))
    }
}