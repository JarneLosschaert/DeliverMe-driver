package be.howest.jarnelosschaert.delivermedriver.logic.models

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id") val id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String
)