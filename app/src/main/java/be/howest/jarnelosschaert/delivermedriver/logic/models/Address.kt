package be.howest.jarnelosschaert.delivermedriver.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val id: Int,
    val street: String,
    val number: String,
    val zip: String,
    val city: String,
    val lat: Double,
    val lon: Double,
)