package be.howest.jarnelosschaert.delivermedriver.logic.models

import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val id: Int,
    val walletAddress: String,
    val person: Person,
)