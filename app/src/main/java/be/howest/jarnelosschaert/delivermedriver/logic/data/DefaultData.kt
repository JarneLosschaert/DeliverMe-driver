package be.howest.jarnelosschaert.delivermedriver.logic.data

import be.howest.jarnelosschaert.delivermedriver.logic.models.*
import java.time.LocalDateTime

val defaultPerson = Person(-1, "", "", "")
val defaultAddress = Address(-1, "", "", "", "", 0.0, 0.0)
val defaultCustomer = Customer(-1, defaultAddress, defaultPerson, listOf())
val defaultDriver = Driver(-1,"", defaultPerson)
val defaultPackage = Package(-1, defaultCustomer, defaultCustomer, defaultAddress, defaultAddress, PackageSize.SMALL, "", 0.0, 0.0, 0.0)
val defaultDelivery = Delivery(-1, LocalDateTime.now(), LocalDateTime.now(), DeliveryState.FAILED, defaultPackage)

val defaultContactPerson = Person(-1, "No contacts yet", "", "")
val defaultContactAddress = Address(-1, "Street", "Nr", "Zip", "City" , 0.0, 0.0)
val defaultContact = Customer(-1, defaultContactAddress, defaultContactPerson, listOf())
