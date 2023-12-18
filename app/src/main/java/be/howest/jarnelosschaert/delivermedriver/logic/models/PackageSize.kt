package be.howest.jarnelosschaert.delivermedriver.logic.models

import com.google.gson.annotations.SerializedName

enum class PackageSize(val value: String) {
    @SerializedName("letter")
    LETTER("letter"),

    @SerializedName("small")
    SMALL("small"),

    @SerializedName("medium")
    MEDIUM("medium"),

    @SerializedName("large")
    LARGE("large"),
}