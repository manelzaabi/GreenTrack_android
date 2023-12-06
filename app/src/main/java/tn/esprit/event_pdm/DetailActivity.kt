package tn.esprit.event_pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import tn.esprit.event_pdm.models.EventItem


import com.squareup.picasso.Picasso
import tn.esprit.event_pdm.models.Event_ID_EXTRA

class DetailActivity : AppCompatActivity() {
    private lateinit var coverImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var joinButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        coverImageView = findViewById(R.id.image)
        titleTextView = findViewById(R.id.title)
        dateTextView = findViewById(R.id.date)
        descriptionTextView = findViewById(R.id.description)
        joinButton = findViewById(R.id.button)

        val eventID = intent.getStringExtra(Event_ID_EXTRA)

        val event = getEventFromDatabase(eventID)

        event?.let {
            Picasso.get().load(it.image).into(coverImageView)
            titleTextView.text = it.title

            dateTextView.text = it.date
            descriptionTextView.text = it.description

        }
    }

    private fun getEventFromDatabase(eventID: String?): EventItem? {

        return null
    }
}
