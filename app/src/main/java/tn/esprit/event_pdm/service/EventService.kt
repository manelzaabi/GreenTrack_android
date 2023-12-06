package tn.esprit.event_pdm.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tn.esprit.event_pdm.models.EventItem


interface EventService {
    @GET("event")
    fun getEvents(): Call<List<EventItem>>

    @POST("events")
    fun addEvent(@Body event: EventItem): Call<EventItem>


}
