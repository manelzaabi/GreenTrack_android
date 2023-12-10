package tn.esprit.nascar.service

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import tn.esprit.nascar.model.ConsomationResponse
import tn.esprit.nascar.model.Consommation

interface ConsommationService {



    data class ConsommationBody(val type: String, val valeur: Double)


    @POST("/Consom/add")
    fun add(@Body consommationBody: ConsommationBody): Call<ConsomationResponse>
}