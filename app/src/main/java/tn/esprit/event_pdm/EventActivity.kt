package tn.esprit.event_pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import tn.esprit.event_pdm.adapters.CardAdapter
import tn.esprit.event_pdm.databinding.ActivityEventBinding
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.models.Event_ID_EXTRA
import tn.esprit.event_pdm.models.eventList
import tn.esprit.event_pdm.repositories.EventRepository
import tn.esprit.event_pdm.service.RetrofitClient
import tn.esprit.event_pdm.viewmodels.EventViewModel

class EventActivity : AppCompatActivity(), EventClickListener {
    private lateinit var binding: ActivityEventBinding
    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val eventService = RetrofitClient.getEventService()
        val repository = EventRepository(eventService)

        val factory = EventViewModelFactory(repository)
        eventViewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)

        val eventActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
            adapter = CardAdapter(eventList, eventActivity)
        }

        val addEventButton = binding.root.findViewById<Button>(R.id.addEventButton)
        addEventButton.setOnClickListener {
            val intent = Intent(applicationContext, Activity_Add::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(event: EventItem) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(Event_ID_EXTRA, event.id)
        startActivity(intent)
    }
}



