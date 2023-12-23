package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import be.howest.jarnelosschaert.delivermedriver.R
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Content
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.SubTitle
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Title
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.functions.showAddress
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    deliveries: List<Delivery>,
    sort: String,
    refreshing: Boolean,
    onDeliveryTap: (Delivery) -> Unit,
    onSortChange: (String) -> Unit,
    onRefreshDeliveries: () -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title()
            SortOptions(sort = sort, onSortChange = onSortChange)
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = onRefreshDeliveries,
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    content = {
                    item {
                        SubTitle(text = "Deliveries")
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
    sort: String,
    onSortChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        SortOption(image = R.drawable.route, onClick = { onSortChange("route_asc") }, selected = sort == "route_asc")
        SortOption(image = R.drawable.clock, onClick = { onSortChange("time_asc") }, selected = sort == "time_asc")
        SortOption(image = R.drawable.clock, onClick = { onSortChange("time_desc") }, ascending = false, selected = sort == "time_desc")
    }
    Box(modifier = Modifier.height(20.dp))
}

@Composable
fun SortOption(
    image: Int,
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
        ) {
            val color =
                if (selected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(color)
            )
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
        DeliveryDetail(label = "From", content = showAddress(delivery.packageInfo.senderAddress))
        DeliveryDetail(label = "To", content = showAddress(delivery.packageInfo.receiverAddress))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DeliveryDetail(label = "Time", content = "10 min", withSpacer = false)
            DeliveryDetail(label = "Payment", content = "€ ${delivery.packageInfo.fee}", withSpacer = false)
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}