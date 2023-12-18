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
    val walletAddressRegex = "^(0x)?[\\da-fA-F]{40}\$"
    return walletAddress.matches(walletAddressRegex.toRegex())
}

fun checkAddress(Address: Address): List<String> {
    val errors = mutableListOf<String>()

    if (Address.street.isBlank()) {
        errors.add("Street is required.")
    } else if (!isValidStreet(Address.street)) {
        errors.add("Invalid street format.")
    }

    if (Address.number.isBlank()) {
        errors.add("Number is required.")
    } else if (!isValidNumber(Address.number)) {
        errors.add("Invalid number format.")
    }

    if (Address.zip.isBlank()) {
        errors.add("Zip code is required.")
    } else if (!isValidPostalCode(Address.zip)) {
        errors.add("Invalid zip code format.")
    }

    if (Address.city.isBlank()) {
        errors.add("City is required.")
    } else if (!isValidCity(Address.city)) {
        errors.add("Invalid city format.")
    }

    return errors
}

private fun isValidPostalCode(zip: String): Boolean {
    val zipRegex = "^[1-9]\\d{3}$"
    return zip.matches(zipRegex.toRegex())
}

private fun isValidStreet(street: String): Boolean {
    
    val streetRegex = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*\$"
    return street.matches(streetRegex.toRegex())
}

private fun isValidNumber(number: String): Boolean {
    val numberRegex = "^\\d+[a-zA-Z]*\$"
    return number.matches(numberRegex.toRegex())
}

private fun isValidCity(city: String): Boolean {
    val cityRegex = "^[A-Za-z]+(?:[\\s-][A-Za-z]+)*\$"
    return city.matches(cityRegex.toRegex())
}

fun checkPackage(description: String): List<String> {
    val errors = mutableListOf<String>()
    if (description.isBlank()) {
        errors.add("Description is required.")
    }
    return errors
}