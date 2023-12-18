package be.howest.jarnelosschaert.delivermedriver.logic.services.requests

import com.google.gson.annotations.SerializedName

data class UpdateDriverRequest(
    @SerializedName("Name") val username: String,
    @SerializedName("Email") val email: String,
    @SerializedName("Phone") val phone: String,
    @SerializedName("WalletAddress") val street: String,
)