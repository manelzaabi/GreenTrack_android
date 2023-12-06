package tn.esprit.event_pdm.models

const val Event_ID_EXTRA = "EVENT_ID_EXTRA"
var eventList = mutableListOf<EventItem>()
data class EventItem(
    val date: String,
    val description: String,
    val details: String,
    val id: String,
    val image: String,
    val isFree: Boolean,
    val location: String,
    val organisateurs: List<Any>,
    val participants: List<Any>,
    val price: String,
    val title: String
)