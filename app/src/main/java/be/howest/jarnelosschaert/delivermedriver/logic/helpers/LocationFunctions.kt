package be.howest.jarnelosschaert.delivermedriver.logic.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import java.lang.Math.toRadians
import com.google.android.gms.location.LocationServices

@SuppressLint("MissingPermission")
fun getLocation(
    context: Context,
    onLocationResult: (Location?) -> Unit
) {
    val locationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    locationProviderClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            onLocationResult(location)
        }
        .addOnFailureListener {
            onLocationResult(null)
        }
}

fun sortDeliveriesByDistance(userLocation: Location, deliveries: List<Delivery>): List<Delivery> {
    return deliveries.sortedBy { delivery ->
        calculateDistance(
            userLocation.latitude,
            userLocation.longitude,
            delivery.`package`.senderAddress.lat,
            delivery.`package`.senderAddress.lon
        )
    }
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val radius = 6371
    val dLat = toRadians(lat2 - lat1)
    val dLon = toRadians(lon2 - lon1)
    val a =
        kotlin.math.sin(dLat / 2) * kotlin.math.sin(dLat / 2) + kotlin.math.cos(toRadians(lat1)) * kotlin.math.cos(
            toRadians(lat2)
        ) * kotlin.math.sin(dLon / 2) * kotlin.math.sin(dLon / 2)
    val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
    return radius * c
}