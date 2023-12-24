package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.delivermedriver.logic.models.Address
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.logic.models.DeliveryState
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.GeneralButton
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.SubTitle
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Title
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.functions.showAddress

@Composable
fun DeliveringScreen(
    modifier: Modifier = Modifier,
    delivery: Delivery,
    onReceivedTap: () -> Unit,
    onDeliveredTap: () -> Unit,
    onNavigateTap: (Address) -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title()
            SubTitle(text = "Active delivery")
            DeliveryDetail(label = "Sender", content = delivery.packageInfo.sender.person.name)
            DeliveryDetail(
                label = "Address (sender)",
                content = showAddress(delivery.packageInfo.senderAddress)
            )
            DeliveryDetail(label = "Receiver", content = delivery.packageInfo.receiver.person.name)
            DeliveryDetail(
                label = "Address (receiver)",
                content = showAddress(delivery.packageInfo.receiverAddress)
            )
            DeliveryDetail(label = "Expected delivery time", content = "10 min")
            DeliveryDetail(label = "Payment", content = "€ ${delivery.packageInfo.fee}")
            GeneralButton(
                text = "Navigate",
                onTap = {
                    if (delivery.state == DeliveryState.ASSIGNED) {
                        onNavigateTap(delivery.packageInfo.senderAddress)
                    } else {
                        onNavigateTap(delivery.packageInfo.receiverAddress)
                    }
                })
            if (delivery.state == DeliveryState.ASSIGNED) {
                GeneralButton(
                    text = "Package received",
                    onTap = onReceivedTap
                )
            }
            if (delivery.state == DeliveryState.TRANSIT) {
                GeneralButton(
                    text = "Package delivered",
                    onTap = onDeliveredTap
                )
            }
        }
    }
}