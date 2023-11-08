package be.howest.jarnelosschaert.delivermedriver.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue,
    onPrimary = White,
    primaryVariant = Red,
    secondary = DarkBlue,
    onSecondary = DarkGrey,
    background = White,
    onBackground = Black,
    surface = Blue,
    onSurface = White,
)

private val LightColorPalette = lightColors(
    primary = Blue,
    onPrimary = White,
    primaryVariant = Red,
    secondary = DarkBlue,
    onSecondary = DarkGrey,
    background = White,
    onBackground = Black,
    surface = Blue,
    onSurface = White,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DeliverMedriverTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}