package tn.esprit.event_pdm.repositories
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.models.Mydata
import tn.esprit.event_pdm.service.EventApiService


class EventRepository(private val eventApiService: EventApiService) {


    fun getAllEvents(): LiveData<List<Mydata>> {
        val data = MutableLiveData<List<Mydata>>()

        eventApiService.getEvents().enqueue(object : Callback<List<Mydata>> {
            override fun onResponse(call: Call<List<Mydata>>, response: Response<List<Mydata>>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                } else {
                    // Handle error
                    Log.e("EventRepoitory", "Error fetching events: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Mydata>>, t: Throwable) {
                // Handle failure
                Log.e("EventRepository", "Network error: ${t.message}")
            }
        })

        return data
    }


    fun addEvent(event: EventItem): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        eventApiService.addEvent(event).enqueue(object : Callback<EventItem> {
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



}
