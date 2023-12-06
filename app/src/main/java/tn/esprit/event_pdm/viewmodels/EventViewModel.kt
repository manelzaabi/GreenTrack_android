package tn.esprit.event_pdm.viewmodels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.repositories.EventRepository


class EventViewModel(private val repository: EventRepository) : ViewModel() {

    fun getEvents(): LiveData<List<EventItem>> {
        return repository.getAllEvents()
    }

    fun addEvent(event: EventItem): LiveData<Boolean> {
        return repository.addEvent(event)
    }

}
