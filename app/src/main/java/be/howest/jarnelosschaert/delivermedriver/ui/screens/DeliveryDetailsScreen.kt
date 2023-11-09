package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Content
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Label
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.SmallButton
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Title

@Composable
fun DeliveryDetailsScreen(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Package details", onGoBack = onGoBack, withGoBack = true)

            DeliveryDetail(label = "Sender", content = "Daan Hautekiet")
            DeliveryDetail(label = "Address (sender)", content = "Kortrijksestraat 12, 8500 Kortrijk")
            DeliveryDetail(label = "Receiver", content = "Glenn Callens")
            DeliveryDetail(label = "Address (receiver)", content = "Kortrijksestraat 12, 8500 Kortrijk")
            DeliveryDetail(label = "Expected delivery time", content = "10 min")
            DeliveryDetail(label = "Payment", content = "€ 10")

            SmallButton(text = "Accept delivery",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {}
            )
        }
    }
}

@Composable
fun DeliveryDetail(label: String, content: String) {
    Label(text = label)
    Content(text = content)
    Spacer(modifier = Modifier.height(10.dp))
}