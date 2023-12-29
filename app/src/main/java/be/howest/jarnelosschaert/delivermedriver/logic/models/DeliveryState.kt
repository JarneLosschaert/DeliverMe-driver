package be.howest.jarnelosschaert.delivermedriver.logic.models

import kotlinx.serialization.Serializable

@Serializable
enum class DeliveryState constructor(val value: String) {
    paid("Delivery paid.\nSearching for driver..."),
    failed("Delivery failed."),
    assigned("A driver has accepted your package.\nWaiting for pickup..."),
    transit("Your package is on its way!"),
    delivered("Your package has been delivered."),
}