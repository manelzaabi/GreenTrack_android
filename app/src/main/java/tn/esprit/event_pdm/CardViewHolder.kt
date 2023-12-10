package tn.esprit.event_pdm

import androidx.recyclerview.widget.RecyclerView
import tn.esprit.event_pdm.databinding.CardCellBinding
import com.squareup.picasso.Picasso
import tn.esprit.event_pdm.models.EventItem


class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: EventClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{

    val baseUrl = "http://192.168.1.199:8000"
    fun bindEvent(events: EventItem) {
    val imagePath = baseUrl+events.image
        Picasso.get().load(imagePath).resize(800,600).centerCrop().into(cardCellBinding.image)
        cardCellBinding.title.text = events.title

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(events)
        }
    }
}
