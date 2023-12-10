package tn.esprit.nascar.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {


    const val BASE_URL =  "http://10.0.2.2:8000/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }



    val consommationService: ConsommationService by lazy {
        retrofit().create(ConsommationService::class.java)
    }



}