package tn.esprit.event_pdm

import androidx.recyclerview.widget.RecyclerView
import tn.esprit.event_pdm.EventClickListener
import tn.esprit.event_pdm.databinding.CardCellBinding
import com.squareup.picasso.Picasso
import tn.esprit.event_pdm.models.EventItem


class CardViewHolder(
    private val cardCellBinding: CardCellBinding,
    private val clickListener: EventClickListener
) : RecyclerView.ViewHolder(cardCellBinding.root)
{
    fun bindEvent(event: EventItem) {
        Picasso.get().load(event.image).into(cardCellBinding.image)
        cardCellBinding.title.text = event.title

        cardCellBinding.cardView.setOnClickListener {
            clickListener.onClick(event)
        }
    }
}
