package tn.esprit.event_pdm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import tn.esprit.event_pdm.adapters.CardAdapter
import tn.esprit.event_pdm.databinding.ActivityEventBinding
import tn.esprit.event_pdm.models.EventItem

import tn.esprit.event_pdm.models.Event_ID_EXTRA
import tn.esprit.event_pdm.models.Events
import tn.esprit.event_pdm.models.eventList
import tn.esprit.event_pdm.repositories.EventRepository
import tn.esprit.event_pdm.service.RetrofitClient
import tn.esprit.event_pdm.viewmodels.EventViewModel
class EventActivity : AppCompatActivity(), EventClickListener {
    private lateinit var binding: ActivityEventBinding
    private lateinit var eventViewModel: EventViewModel
    private lateinit var events: Events // Déclaration de la variable events
    private lateinit var cardAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventActivity = this

        val eventService = RetrofitClient.eventService
        val repository = EventRepository(eventService)

        val factory = EventViewModelFactory(repository)
        eventViewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)


            adapter = CardAdapter(listOf<EventItem>(),this@EventActivity)
        }

        eventViewModel.getEvents().observe(this, { events ->
            cardAdapter = CardAdapter(events, this@EventActivity)
            binding.recyclerView.adapter = cardAdapter
        })

        val addEventButton = binding.root.findViewById<Button>(R.id.addEventButton)
        addEventButton.setOnClickListener {
            val intent = Intent(applicationContext, Activity_Add::class.java)
            startActivity(intent)
        }
    }
    override fun onClick(events: EventItem) {
        val recyclerView = binding.recyclerView // Obtenez la référence de votre RecyclerView depuis votre binding
        val layoutManager = recyclerView.layoutManager as? GridLayoutManager
        val position = layoutManager?.findFirstVisibleItemPosition() ?: 0


        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(Event_ID_EXTRA, events)
        startActivity(intent)
    }

}



