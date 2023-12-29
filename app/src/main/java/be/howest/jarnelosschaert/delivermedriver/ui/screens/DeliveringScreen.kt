package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.delivermedriver.logic.models.Address
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.logic.models.DeliveryState
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.FingerPrintButton
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.SubTitle
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Title
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.functions.showAddress

@Composable
fun DeliveringScreen(
    modifier: Modifier = Modifier,
    delivery: Delivery,
    onGoBack: () -> Unit,
    onReceivedTap: () -> Unit,
    onDeliveredTap: () -> Unit,
    onNavigateTap: (Address) -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Active delivery", onGoBack = onGoBack, withGoBack = true)
            SubTitle(text = "Active delivery")
            DeliveryDetail(label = "Sender", content = delivery.`package`.sender.person.name)
            DeliveryDetail(
                label = "Address (sender)",
                content = showAddress(delivery.`package`.senderAddress)
            )
            DeliveryDetail(label = "Receiver", content = delivery.`package`.receiver.person.name)
            DeliveryDetail(
                label = "Address (receiver)",
                content = showAddress(delivery.`package`.receiverAddress)
            )
            DeliveryDetail(label = "Size", content = delivery.`package`.packageSize.value)
            DeliveryDetail(label = "Distance", content = "${delivery.`package`.distance} km")
            DeliveryDetail(label = "Payment", content = "€ ${delivery.`package`.driverFee}")
            if (delivery.state == DeliveryState.assigned) {
                GeneralButton(
                    text = "Navigate to sender",
                    onTap = { onNavigateTap(delivery.`package`.senderAddress) }
                )
            }
            if (delivery.state == DeliveryState.transit) {
                GeneralButton(
                    text = "Navigate to receiver",
                    onTap = { onNavigateTap(delivery.`package`.receiverAddress) }
                )
            }
            if (delivery.state == DeliveryState.assigned) {
                FingerPrintButton(text = "Package received", onSuccess = onReceivedTap)
            }
            if (delivery.state == DeliveryState.transit) {
                FingerPrintButton(text = "Package delivered", onSuccess = onDeliveredTap)
            }
        }
    }
}