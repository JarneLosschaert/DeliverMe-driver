package be.howest.jarnelosschaert.delivermedriver.logic.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri

class DirectionsApi
{
    companion object
    {
        fun openGoogleMapsNavigationToB(context: Context,
                                        latitude : Double,
                                        longitude : Double)
        {
            val googleMapsUrl = "google.navigation:q=$latitude,$longitude"
            val uri = Uri.parse(googleMapsUrl)

            val googleMapsPackage = "com.google.android.apps.maps"
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage(googleMapsPackage)
            }

            context.startActivity(intent)
        }

        fun openGoogleMapsNavigationFromAToB(context: Context,
                                             originLatitude : Double,
                                             originLongitude : Double,
                                             destinationLatitude : Double,
                                             destinationLongitude : Double)
        {
            val googleMapsUrl = "https://www.google.com/maps/dir/?api=1&amp;" +
                    "origin=$originLatitude," +
                    "$originLongitude&amp;" +
                    "destination=$destinationLatitude," +
                    "$destinationLongitude"
            val uri = Uri.parse(googleMapsUrl)

            val googleMapsPackage = "com.google.android.apps.maps"
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage(googleMapsPackage)
            }

            context.startActivity(intent)
        }
    }
}