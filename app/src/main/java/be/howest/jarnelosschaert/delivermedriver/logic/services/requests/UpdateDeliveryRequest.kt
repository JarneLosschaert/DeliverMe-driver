package be.howest.jarnelosschaert.delivermedriver.logic.services.requests

import be.howest.jarnelosschaert.delivermedriver.logic.models.DeliveryState
import com.google.gson.annotations.SerializedName

data class UpdateDeliveryRequest(
    @SerializedName("State") val state: DeliveryState,
)