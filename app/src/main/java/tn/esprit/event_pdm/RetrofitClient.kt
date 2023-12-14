package tn.esprit.event_pdm

import android.util.Log
import com.example.videos.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://172.16.5.147:8000/"

    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Log.d("ApiEndpoint", "API Endpoint: ${retrofit.baseUrl()}video/add")

        retrofit.create(ApiService::class.java)
    }
}

