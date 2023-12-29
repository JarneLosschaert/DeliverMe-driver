package be.howest.jarnelosschaert.delivermedriver.logic.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: Int,
    var name: String,
    val email: String,
    val phone: String
)