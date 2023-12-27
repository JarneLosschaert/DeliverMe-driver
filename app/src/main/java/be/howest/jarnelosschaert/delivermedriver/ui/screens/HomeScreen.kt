package be.howest.jarnelosschaert.delivermedriver.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Content
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.SubTitle
import be.howest.jarnelosschaert.delivermedriver.ui.helpers.components.Title
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    deliveries: List<Delivery>,
    refreshing: Boolean,
    onDeliveryTap: (Delivery) -> Unit,
    onRefreshDeliveries: () -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column {
            Title()
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = refreshing),
                onRefresh = onRefreshDeliveries,
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    content = {
                        item {
                            SubTitle(text = "Active Deliveries")
                            for (delivery in deliveries) {
                                DeliveryCard(
                                    delivery = delivery,
                                    onDeliveryTap = { onDeliveryTap(delivery) }
                                )
                            }
                            if (deliveries.isEmpty()) {
                                Content(text = "No active deliveries yet")
                            }
                        }
                    })
            }
        }
    }
}
