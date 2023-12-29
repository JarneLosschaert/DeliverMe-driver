package be.howest.jarnelosschaert.delivermedriver.logic.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.*
import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

class LocationUpdateWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    override fun doWork(): Result {
        Log.d(TAG, "LocationUpdateWorker is running")

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val email = inputData.getString("email")
                    val delivery = Gson().fromJson(inputData.getString("delivery"), Delivery::class.java)
                    handleLocationUpdate(email!!, delivery, location.latitude, location.longitude)
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to get location: ${e.message}")
            }

        return Result.success()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun handleLocationUpdate(email: String, delivery: Delivery, latitude: Double, longitude: Double) {
        Log.d(TAG, "User location: $latitude, $longitude")
        val webSocket = WebSocketManager(CoroutineScope(coroutineContext), email)
        webSocket.sendLocationUpdate(delivery, latitude, longitude)

        val deliveryJson = Gson().toJson(delivery)
        val inputData = Data.Builder()
            .putString("email", email)
            .putString("delivery", deliveryJson)
            .build()

        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(LocationUpdateWorker::class.java)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    companion object {
        private const val TAG = "LocationUpdateWorker"
    }
}
