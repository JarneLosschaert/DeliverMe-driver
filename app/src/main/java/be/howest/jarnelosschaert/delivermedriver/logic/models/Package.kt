package be.howest.jarnelosschaert.delivermedriver.logic.models

import com.google.gson.annotations.SerializedName

data class Package(
    @SerializedName("id") val id: Int,
    @SerializedName("sender") val sender: Customer,
    @SerializedName("receiver") val receiver: Customer,
    @SerializedName("senderAddress") val senderAddress: Address,
    @SerializedName("receiverAddress") val receiverAddress: Address,
    @SerializedName("packageSize") val packageSize: PackageSize,
    @SerializedName("description") val description: String,
    @SerializedName("fee") val fee: Double,
    @SerializedName("driverFee") val driverFee: Double,
    @SerializedName("distance") val distance: Double,
)