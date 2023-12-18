package be.howest.jarnelosschaert.delivermedriver.ui.helpers.functions

import be.howest.jarnelosschaert.deliverme.logic.models.Address
import be.howest.jarnelosschaert.delivermedriver.logic.models.Driver
import java.time.format.DateTimeFormatter

fun showAddress(address: Address): String {
    return "${address.street} ${address.number},\n${address.zip} ${address.city}"
}

fun showPhoneNumber(phoneNumber: String): String {
    if (phoneNumber.length == 10) {
        return "${phoneNumber.substring(0, 4)} ${phoneNumber.substring(4, 6)} ${phoneNumber.substring(6, 8)} ${phoneNumber.substring(8, 10)}"
    }
    return phoneNumber
}

fun showWalletAddress(walletAddress: String): String {
    if (walletAddress.length == 16) {
        return "${walletAddress.substring(0, 4)} ${walletAddress.substring(4, 8)} ${walletAddress.substring(8, 12)} ${walletAddress.substring(12, 16)}"
    }
    return walletAddress
}

val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")