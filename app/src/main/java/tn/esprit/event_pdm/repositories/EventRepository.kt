package tn.esprit.event_pdm.repositories
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.service.EventService


class EventRepository(private val eventService: EventService) {
    fun getAllEvents(): LiveData<List<EventItem>> {
        val data = MutableLiveData<List<EventItem>>()

        eventService.getEvents().enqueue(object : Callback<List<EventItem>> {
            override fun onResponse(call: Call<List<EventItem>>, response: Response<List<EventItem>>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                } else {
                    // Handle error
                    Log.e("EventRepoitory", "Error fetching events: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<EventItem>>, t: Throwable) {
                // Handle failure
                Log.e("EventRepository", "Network error: ${t.message}")
            }
        })

        return data
    }


    fun addEvent(event: EventItem): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        eventService.addEvent(event).enqueue(object : Callback<EventItem> {
            override fun onResponse(call: Call<EventItem>, response: Response<EventItem>) {
                result.value = response.isSuccessful
            }

            override fun onFailure(call: Call<EventItem>, t: Throwable) {
                // Handle failure
                result.value = false
            }
        })

        return result
    }


    fun updateEvent(eventId: String, updatedEvent: EventItem): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()


        return result
    }


    fun deleteEvent(eventId: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()



        return result
    }
}
