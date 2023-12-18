package be.howest.jarnelosschaert.delivermedriver.logic.services.responses

import be.howest.jarnelosschaert.delivermedriver.logic.models.Customer
import be.howest.jarnelosschaert.delivermedriver.logic.models.Driver
import com.google.gson.annotations.SerializedName

data class RegistrationLoginResponse(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("driver") val driver: Driver
)