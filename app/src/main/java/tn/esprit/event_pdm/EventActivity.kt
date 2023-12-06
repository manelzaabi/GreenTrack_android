package tn.esprit.event_pdm

import tn.esprit.event_pdm.viewmodels.EventViewModel
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.fragment.app.Fragment


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.event_pdm.EventClickListener
import tn.esprit.event_pdm.R
import tn.esprit.event_pdm.databinding.ActivityEventBinding
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.adapters.CardAdapter
import tn.esprit.event_pdm.service.RetrofitClient

class EventActivity : Fragment(R.layout.activity_event), EventClickListener {

    private var _binding: ActivityEventBinding? = null

    private val binding get() = _binding!!

    private lateinit var eventAdapter: CardAdapter
    private val eventViewModel: EventViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeEvents()
        fetchDataFromApi()

        binding.addEventButton.setOnClickListener {
            // Logic to navigate to Add Event Activity
        }
    }

    private fun setupRecyclerView() {
        eventAdapter = CardAdapter(emptyList(), this)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = eventAdapter
        }
    }

    private fun observeEvents() {
        eventViewModel.getEvents().observe(viewLifecycleOwner, Observer { events ->
            eventAdapter.updateEvents(events)
        })
    }

    private fun fetchDataFromApi() {
        val eventService = RetrofitClient.getEventService()
        val call: Call<List<EventItem>> = eventService.getEvents()

        call.enqueue(object : Callback<List<EventItem>> {
            override fun onResponse(call: Call<List<EventItem>>, response: Response<List<EventItem>>) {
                if (response.isSuccessful) {
                    val events: List<EventItem>? = response.body()
                    eventAdapter.updateEvents(events ?: emptyList())
                } else {
                    Log.d("EventActivity", "Failed to fetch events: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<EventItem>>, t: Throwable) {
                Log.e("EventActivity", "Network error: ${t.message}")
            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(event: EventItem) {
        // Logic to handle event click and navigate to detail activity
    }
}
