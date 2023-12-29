package be.howest.jarnelosschaert.delivermedriver.logic.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: Int,
    val homeAddress: Address,
    val person: Person,
    val contacts: List<Customer> = listOf(),
)