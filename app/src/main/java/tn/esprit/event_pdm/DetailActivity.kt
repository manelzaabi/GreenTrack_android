package tn.esprit.event_pdm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import tn.esprit.event_pdm.models.EventItem
import tn.esprit.event_pdm.models.Event_ID_EXTRA
/*import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.model.PaymentIntent
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.view.PaymentMethodsActivityStarter*/


class DetailActivity : AppCompatActivity() {
    private lateinit var coverImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var freeTextView: TextView

    private lateinit var dateTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var detailsTextView: TextView
    private lateinit var joinButton: Button
    private lateinit var locTV: TextView
    val baseUrl = "http://192.168.1.34:8000"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        coverImageView = findViewById(R.id.image)
        titleTextView = findViewById(R.id.title)
        dateTextView = findViewById(R.id.date)
        descriptionTextView = findViewById(R.id.description)
        detailsTextView = findViewById(R.id.details)
        joinButton = findViewById(R.id.button)
        locTV = findViewById(R.id.loc)
        freeTextView = findViewById(R.id.free)
        val event = intent.getParcelableExtra<EventItem>(Event_ID_EXTRA)

        val userId = "1"
        val eventId = event?.id

        val isUserJoined = isUserJoined(userId, eventId!!)
        updateButtonState(isUserJoined)

        joinButton.setOnClickListener {


            if (isUserJoined) {
                cancelParticipation(userId, eventId)
                updateButtonState(false)
            } else {
                participerEvenement(userId, eventId)
                updateButtonState(true)
            }
        }
        //configureStripe()

        event?.let {
            val imagePath = baseUrl+it.image
            Picasso.get().load(imagePath).resize(800,600).centerCrop().into(coverImageView)
            titleTextView.text = it.title

            dateTextView.text = it.date
            descriptionTextView.text = it.description
            detailsTextView.text=it.details
            locTV.text=it.location

            if (it.isFree) {
                freeTextView.text = "Free Event"
            } else {
                if (it.price != null) {
                    freeTextView.text = "Paid Event: $${it.price}"
                } else {
                    freeTextView.text = "Paid Event"
                }
            }

        }
    }

    /*private fun configureStripe() {
        PaymentConfiguration.init(applicationContext, "pk_test_51OLxpIJdpkdPaLkKioX4BX9R0xk1LLhB8GeoquhYnQ0pUjPRgkWGN0Qp795VSrErXz3QSBYfrVD8q5yIkmYYZIU000CeU5YhKC")
        val stripeApiKey = "sk_test_51OLxpIJdpkdPaLkKrfp3DqhIGJOydzFT0Nd4FESfHLPZwNIGQX7PY6UAeWJEK2JhwF0RB0cdVu59VLVACCPBpiK300x63QUCei"
        Stripe.apiKey = stripeApiKey

        fun startStripePayment() {

            val paymentIntentParams = PaymentIntent.Params.createWithAmount(1000L, "usd")

            val paymentIntent = PaymentIntent.create(paymentIntentParams)

            val paymentMethodParams = PaymentMethodCreateParams.create(paymentIntent.clientSecret)

            val paymentMethodsActivityStarter = PaymentMethodsActivityStarter.HostedPaymentMethod
                .ParamsBuilder()
                .setPaymentMethodCreateParams(paymentMethodParams)
                .build()

            val paymentMethodsActivityLauncher = PaymentMethodsActivityStarter(this)
            paymentMethodsActivityLauncher.startForResult(paymentMethodsActivityStarter)
        }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            } else {

            }
        }

    }
*/
    private fun cancelParticipation(userId: String, eventId: String) {
        val sharedPreferences = getSharedPreferences("UserEvents", Context.MODE_PRIVATE)
        val userEventKey = "user_$userId"

        val userEvents = sharedPreferences.getStringSet(userEventKey, HashSet())?.toMutableSet()
        userEvents?.remove(eventId)

        sharedPreferences.edit().putStringSet(userEventKey, userEvents).apply()

        println("L'utilisateur avec l'ID $userId a annulé sa participation à l'événement avec l'ID $eventId.")
    }

    private fun updateButtonState(isJoined: Boolean) {
        if (isJoined) {
            joinButton.text = "Joined"
            // Mettez ici le style ou la couleur que vous souhaitez pour indiquer que l'utilisateur a rejoint l'événement
        } else {
            joinButton.text = "Join"
            // Mettez ici le style ou la couleur par défaut du bouton
        }
    }

    private fun isUserJoined(userId: String, eventId: String): Boolean {
        val sharedPreferences = getSharedPreferences("UserEvents", Context.MODE_PRIVATE)
        val userEventKey = "user_$userId"

        val userEvents = sharedPreferences.getStringSet(userEventKey, HashSet()) ?: HashSet()
        return userEvents.contains(eventId)
    }


    private fun participerEvenement(userId: String, eventId: String) {
        val sharedPreferences = getSharedPreferences("UserEvents", Context.MODE_PRIVATE)
        val userEventKey = "user_$userId"

        val userEvents = sharedPreferences.getStringSet(userEventKey, HashSet())?.toMutableSet()
        userEvents?.add(eventId)

        sharedPreferences.edit().putStringSet(userEventKey, userEvents).apply()

        println("L'utilisateur avec l'ID $userId a rejoint l'événement avec l'ID $eventId.")



    }
    private fun getEventsForUser(userId: String): Set<String>? {
        val sharedPreferences = getSharedPreferences("UserEvents", Context.MODE_PRIVATE)
        val userEventKey = "user_$userId"

        return sharedPreferences.getStringSet(userEventKey, null)
    }








}
