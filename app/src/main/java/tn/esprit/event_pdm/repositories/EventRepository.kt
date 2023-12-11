package tn.esprit.event_pdm.repositories
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.models.Events
import tn.esprit.event_pdm.service.EventApiService
import tn.esprit.event_pdm.toRequestBody
import java.io.File


class EventRepository(private val eventApiService: EventApiService) {

    fun getAllEvents(): LiveData<List<EventItem>> {
        val data = MutableLiveData<List<EventItem>>()

        eventApiService.getEvents().enqueue(object : Callback<Events> {
            override fun onResponse(call: Call<Events>, response: Response<Events>) {
                if (response.isSuccessful) {
                    data.value = response.body()?.events
                } else {
                    // Handle error

                    Log.e("EventRepository", "Error fetching events: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Events>, t: Throwable) {
                // Handle failure
                Log.e("EventRepository", "Network error: ${t.message}")
            }
        })

        return data
    }

    fun addEvent(event: EventItem): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val requestBody = HashMap<String, RequestBody>()
        val textMediaType = MediaType.parse("text/plain")


        requestBody["date"] = event.date.toRequestBody(textMediaType)
        requestBody["description"] = event.description.toRequestBody(textMediaType)
        requestBody["details"] = event.details.toRequestBody(textMediaType)
        requestBody["title"] = event.title.toRequestBody(textMediaType)
        requestBody["location"] = event.location.toRequestBody(textMediaType)
        requestBody["isFree"] = event.isFree.toString().toRequestBody(textMediaType)

        val file: File = File("image-1700598973149-918941309.png")

        file.createNewFile();

        val fileBody = RequestBody.create(MediaType.parse("image/*"), file)
        val imagePart =
            MultipartBody.Part.createFormData("image", file.name, fileBody)

        eventApiService.addEvent(imagePart, requestBody).enqueue(object : Callback<EventItem> {
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
