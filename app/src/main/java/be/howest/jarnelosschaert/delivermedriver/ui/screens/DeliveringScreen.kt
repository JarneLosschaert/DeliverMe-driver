package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    onGoBack: () -> Unit,
    onReceivedTap: () -> Unit,
    onDeliveredTap: () -> Unit,
    onNavigateTap: (Address) -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Active delivery", onGoBack = onGoBack, withGoBack = true)
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
            DeliveryDetail(label = "Size", content = delivery.packageInfo.packageSize.value)
            DeliveryDetail(label = "Distance", content = "${delivery.packageInfo.distance} km")
            DeliveryDetail(label = "Payment", content = "€ ${delivery.packageInfo.driverFee}")
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
                    onTap = onReceivedTap,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            if (delivery.state == DeliveryState.TRANSIT) {
                GeneralButton(
                    text = "Package delivered",
                    onTap = onDeliveredTap,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}