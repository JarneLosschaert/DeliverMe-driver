package be.howest.jarnelosschaert.delivermedriver.logic.helpers

import be.howest.jarnelosschaert.deliverme.logic.models.Address

fun checkValuesSignUp(
    username: String,
    email: String,
    phone: String,
    walletAddress: String,
    password: String,
    confirmPassword: String
): List<String> {
    val errors = mutableListOf<String>()

    if (username.isBlank()) {
        errors.add("Username is required.")
    }

    if (email.isBlank()) {
        errors.add("Email is required.")
    } else if (!isValidEmail(email)) {
        errors.add("Invalid email format.")
    }

    if (phone.isBlank()) {
        errors.add("Phone number is required.")
    } else if (!isValidPhoneNumber(phone)) {
        errors.add("Invalid phone number format (ex: 0499999999).")
    }

    if (walletAddress.isBlank()) {
        errors.add("Wallet address is required.")
    } else if (!isValidWalletAddress(walletAddress)) {
        errors.add("Invalid wallet address format.")
    }

    if (password.isBlank()) {
        errors.add("Password is required.")
    } else if (password.length < 8) {
        errors.add("Password must be at least 8 characters long.")
    } else if (password != confirmPassword) {
        errors.add("Passwords do not match.")
    }

    return errors
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
    return email.matches(emailRegex.toRegex())
}

private fun isValidPhoneNumber(phone: String): Boolean {
    val cleanedPhoneNumber = phone.replace(Regex("\\D"), "")
    return cleanedPhoneNumber.startsWith("0") && cleanedPhoneNumber.length == 10
}

private fun isValidWalletAddress(walletAddress: String): Boolean {
    val walletAddressRegex = "^BE\\d{14}\$|BE\\d{2} \\d{4} \\d{4} \\d{4}\$"
    return walletAddress.matches(walletAddressRegex.toRegex())
}