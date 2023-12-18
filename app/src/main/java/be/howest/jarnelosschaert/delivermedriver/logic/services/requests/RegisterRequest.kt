package be.howest.jarnelosschaert.delivermedriver.logic.services.requests

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("Name") val username: String,
    @SerializedName("Email") val email: String,
    @SerializedName("Phone") val phone: String,
    @SerializedName("Password") val password: String,
    @SerializedName("WalletAddress") val street: String,
)
