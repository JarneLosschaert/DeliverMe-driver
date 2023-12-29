package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.delivermedriver.R
import be.howest.jarnelosschaert.delivermedriver.logic.data.Sort
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Content
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.SubTitle
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Title
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.functions.HandleLocationPermissions
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.functions.showAddress
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun DeliveriesScreen(
    modifier: Modifier = Modifier,
    deliveries: List<Delivery>,
    sort: Sort,
    refreshing: Boolean,
    onDeliveryTap: (Delivery) -> Unit,
    onSortChange: (Sort) -> Unit,
    onRefreshDeliveries: () -> Unit,
) {
    var granted by remember { mutableStateOf(false) }
    HandleLocationPermissions(onPermission = { granted = it })
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title(text = "Deliveries")
            SortOptions(sort = sort, onSortChange = onSortChange, granted = granted)
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = onRefreshDeliveries,
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    content = {
                        item {
                            SubTitle(text = "Search Deliveries")
                            for (delivery in deliveries) {
                                DeliveryCard(
                                    delivery = delivery,
                                    onDeliveryTap = { onDeliveryTap(delivery) }
                                )
                            }
                            if (deliveries.isEmpty()) {
                                Content(text = "No deliveries found")
                            }
                        }
                    })
            }
        }
    }
}

@Composable
fun SortOptions(
    granted: Boolean,
    sort: Sort,
    onSortChange: (Sort) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        SortOption(
            onClick = { onSortChange(Sort.DISTANCE_DESC) },
            selected = sort == Sort.DISTANCE_DESC,
            isDistance = true
        )
        SortOption(
            onClick = { onSortChange(Sort.DISTANCE_ASC) },
            ascending = false,
            selected = sort == Sort.DISTANCE_ASC,
            isDistance = true
        )
        if (granted) {
            SortOption(onClick = { onSortChange(Sort.CLOSEST) }, selected = sort == Sort.CLOSEST)
        }
    }
    Box(modifier = Modifier.height(20.dp))
}

@Composable
fun SortOption(
    image: Int = R.drawable.route,
    text: String = "km",
    isDistance: Boolean = false,
    ascending: Boolean = true,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val borderColor =
        if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
    Box(
        modifier = Modifier
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .background(
                if (selected) MaterialTheme.colors.primary else Color.White,
                RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val color =
                if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
            if (isDistance) {
                Content(text = text, color = color)
            } else {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(color)
                )
            }
            if (ascending) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_up),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(color)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.arrow_down),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(color)
                )
            }
        }
    }
    Box(modifier = Modifier.width(10.dp))
}

@Composable
fun DeliveryCard(
    delivery: Delivery,
    onDeliveryTap: () -> Unit
) {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(10.dp)
            .clickable(onClick = onDeliveryTap),
    ) {
        DeliveryDetail(label = "From", content = showAddress(delivery.`package`.senderAddress))
        DeliveryDetail(label = "To", content = showAddress(delivery.`package`.receiverAddress))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DeliveryDetail(
                label = "Distance",
                content = "${delivery.`package`.distance} km",
                withSpacer = false
            )
            DeliveryDetail(
                label = "Payment",
                content = "€ ${delivery.`package`.driverFee}",
                withSpacer = false
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}