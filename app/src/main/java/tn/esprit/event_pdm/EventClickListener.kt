package tn.esprit.event_pdm


import tn.esprit.event_pdm.models.Events


interface EventClickListener
{
    fun onClick(event: Events)
}