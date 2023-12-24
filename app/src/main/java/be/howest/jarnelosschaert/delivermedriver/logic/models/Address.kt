package be.howest.jarnelosschaert.delivermedriver.logic.models

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("id") val id: Int,
    @SerializedName("street") val street: String,
    @SerializedName("number") val number: String,
    @SerializedName("zip") val zip: String,
    @SerializedName("city") val city: String,
    @SerializedName("lat") val country: Double,
    @SerializedName("lon") val long: Double,
)