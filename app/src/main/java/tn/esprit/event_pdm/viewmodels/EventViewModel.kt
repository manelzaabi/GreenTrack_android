package tn.esprit.event_pdm.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.models.Events
import tn.esprit.event_pdm.repositories.EventRepository

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    fun getEvents(): LiveData<List<EventItem>> {
        return repository.getAllEvents()
    }

    fun addEvent(event: EventItem, uri: Uri, context:Context): LiveData<Boolean> {
        return repository.addEvent(event,uri,context)
    }
}

