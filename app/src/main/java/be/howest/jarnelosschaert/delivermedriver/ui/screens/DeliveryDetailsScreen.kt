package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.*
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.functions.showAddress

@Composable
fun DeliveryDetailsScreen(
    modifier: Modifier = Modifier,
    delivery: Delivery,
    onAssignTap: () -> Unit,
    onGoBack: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Delivery details", onGoBack = onGoBack, withGoBack = true)
            DeliveryDetail(label = "Sender", content = delivery.`package`.sender.person.name)
            DeliveryDetail(label = "Address (sender)", content = showAddress(delivery.`package`.senderAddress))
            DeliveryDetail(label = "Receiver", content = delivery.`package`.receiver.person.name)
            DeliveryDetail(label = "Address (receiver)", content = showAddress(delivery.`package`.receiverAddress))
            DeliveryDetail(label = "Size", content = delivery.`package`.packageSize.value)
            DeliveryDetail(label = "Distance", content = "${delivery.`package`.distance} km")
            DeliveryDetail(label = "Payment", content = "€ ${delivery.`package`.driverFee}")

            GeneralButton(text = "Accept delivery",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onTap = onAssignTap
            )
        }
    }
}

@Composable
fun DeliveryDetail(label: String, content: String, withSpacer: Boolean = true) {
    Column {
        Label(text = label)
        Content(text = content)
        if (withSpacer) Spacer(modifier = Modifier.height(10.dp))
    }
}