package tn.esprit.event_pdm

import tn.esprit.event_pdm.models.EventItem

interface EventClickListener {
    fun onClick(events: EventItem)
}
