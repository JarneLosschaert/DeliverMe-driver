package be.howest.jarnelosschaert.delivermedriver.logic.models

import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("id") val id: Int,
    @SerializedName("walletAddress") val walletAddress: String,
    @SerializedName("person") val person: Person,
)