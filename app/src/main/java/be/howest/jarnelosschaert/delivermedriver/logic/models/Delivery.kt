package be.howest.jarnelosschaert.delivermedriver.logic.models

import kotlinx.serialization.Serializable

@Serializable
data class Delivery(
    val id: Int,
    val dateTimeDeparted: String,
    val dateTimeArrived: String,
    val state: DeliveryState,
    val `package`: Package,
    val driver: Driver
)