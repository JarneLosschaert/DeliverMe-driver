package be.howest.jarnelosschaert.delivermedriver.logic.models

import kotlinx.serialization.Serializable

@Serializable
data class Package(
    val id: Int,
    val sender: Customer,
    val receiver: Customer,
    val senderAddress: Address,
    val receiverAddress: Address,
    val packageSize: PackageSize,
    val description: String,
    val distance: Double,
    val fee: Double,
    val driverFee: Double,
)