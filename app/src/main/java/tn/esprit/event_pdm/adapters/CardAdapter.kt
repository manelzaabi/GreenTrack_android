package tn.esprit.event_pdm.adapters
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup

import tn.esprit.event_pdm.EventClickListener
import tn.esprit.event_pdm.databinding.CardCellBinding
import tn.esprit.event_pdm.CardViewHolder
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.models.Events

class CardAdapter(

    private var events: List<EventItem>, // Utilisation de l'objet Events au lieu de List<EventItem>
    private val clickListener: EventClickListener
) : RecyclerView.Adapter<CardViewHolder>() {

    // Mettre à jour les événements dans l'adaptateur
    fun updateEvents(newEvents: List<EventItem>) {
        events = newEvents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardCellBinding.inflate(from, parent, false)
        return CardViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val event = events[position]
        holder.bindEvent(event)
        holder.itemView.setOnClickListener { clickListener.onClick(event) } // Passer la liste d'événements
    }


    override fun getItemCount(): Int = events.size // Utilisation de la taille de la liste d'événements dans l'objet Events
}




