package be.howest.jarnelosschaert.delivermedriver.logic.services.other

import be.howest.jarnelosschaert.delivermedriver.logic.models.Delivery
import be.howest.jarnelosschaert.delivermedriver.logic.models.Driver
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.LoginRequest
import be.howest.jarnelosschaert.delivermedriver.logic.services.responses.RegistrationLoginResponse
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.RegisterRequest
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.UpdateDriverRequest
import be.howest.jarnelosschaert.delivermedriver.logic.services.requests.UpdatePasswordRequest
import retrofit2.http.*

interface ApiService {
    @POST("/drivers")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegistrationLoginResponse
    @POST("/drivers/auth")
    suspend fun loginUser(@Body loginRequest: LoginRequest): RegistrationLoginResponse
    @PUT("/drivers/auth")
    suspend fun changePassword(
        @Header("Authorization") authToken: String,
        @Body updatePasswordRequest: UpdatePasswordRequest,
    )
    @PUT("/drivers/{id}")
    suspend fun updateDriver(
        @Header("Authorization") authToken: String,
        @Path("id") driversId: Int,
        @Body updateDriverRequest: UpdateDriverRequest,
    ): Driver
    @DELETE("/customers/{id}")
    suspend fun deleteDriver(
        @Header("Authorization") authToken: String,
        @Path("id") driverId: Int,
    )
    @GET("/deliveries")
    suspend fun getDeliveries(@Header("Authorization") authToken: String): List<Delivery>
    @POST("/deliveries/{id}/assign")
    suspend fun assignDelivery(
        @Header("Authorization") authToken: String,
        @Path("id") deliveryId: Int,
    ): Delivery
    @PUT("/deliveries/{id}")
    suspend fun updateDelivery(
        @Header("Authorization") authToken: String,
        @Path("id") deliveryId: Int,
        @Body delivery: Delivery,
    ): Delivery
}
