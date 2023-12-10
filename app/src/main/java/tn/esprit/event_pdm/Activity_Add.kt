package tn.esprit.event_pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.DatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.service.RetrofitClient
import java.util.UUID

class Activity_Add : AppCompatActivity() {


    private var collector = ""

    private lateinit var titletxt: EditText
    private lateinit var desctxt: EditText
    private lateinit var loctxt : EditText
    private lateinit var detailsEvent: EditText
    private lateinit var SubmitSave: Button
    private lateinit var Freebtn: RadioButton
    private lateinit var Paidbtn: RadioButton
    private lateinit var datePicker: DatePicker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        titletxt = findViewById(R.id.titletxt)
        desctxt = findViewById(R.id.desctxt)
        loctxt = findViewById(R.id.loctxt)
        detailsEvent = findViewById(R.id.detailsEvent)
        Freebtn = findViewById(R.id.free)
        Paidbtn = findViewById(R.id.paid)
        datePicker = findViewById(R.id.datePicker)
        SubmitSave = findViewById(R.id.btnSubmit)

        SubmitSave.setOnClickListener {
            val title = titletxt.text.toString()
            val description = desctxt.text.toString()
            val location = loctxt.text.toString()
            val details = detailsEvent.text.toString()
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year

            val selectedDate = "$day/$month/$year"

            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || details.isEmpty()) {
                Toast.makeText(this@Activity_Add, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val eventItem = EventItem(
                    date = selectedDate,
                    description = description,
                    details = details,
                    id = UUID.randomUUID().toString(),
                    image = "image-1700598973149-918941309.png",
                    isFree = true,
                    location = location,
                    price = "23",
                    title = title
                )


                RetrofitClient.eventService.addEvent(eventItem).enqueue(object : Callback<EventItem> {
                    override fun onResponse(call: Call<EventItem>, response: Response<EventItem>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@Activity_Add, "Event added successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@Activity_Add, "Failed to add event", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<EventItem>, t: Throwable) {
                        Toast.makeText(this@Activity_Add, "Failed: " + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }}}}


