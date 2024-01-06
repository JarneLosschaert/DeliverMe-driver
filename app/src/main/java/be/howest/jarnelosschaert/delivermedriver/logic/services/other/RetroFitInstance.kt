package be.howest.jarnelosschaert.delivermedriver.logic.services.other

import be.howest.jarnelosschaert.delivermedriver.logic.helpers.LocalDateTimeTypeAdapter
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

object RetrofitInstance {

    private const val BASE_URL = "https://api.deliverme.site/"

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
        .create()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}