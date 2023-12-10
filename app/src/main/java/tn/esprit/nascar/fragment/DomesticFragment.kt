package tn.esprit.nascar.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.nascar.R
import tn.esprit.nascar.service.ApiService
import tn.esprit.nascar.service.ConsommationService
import java.text.DecimalFormat
import com.google.gson.Gson
import tn.esprit.nascar.model.ConsomationResponse


class DomesticFragment : Fragment() {
    private lateinit var editTextElectricityConsumption: EditText
    private lateinit var editTextGasConsumption: EditText
    private lateinit var btnCalculateDomestic: Button
    private lateinit var textViewDomesticResultLabel: TextView
    private lateinit var textViewDomesticResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_domestic, container, false)


        // Initialisez les vues
        editTextElectricityConsumption = view.findViewById(R.id.editTextElectricityConsumption)
        editTextGasConsumption = view.findViewById(R.id.editTextGasConsumption)
        btnCalculateDomestic = view.findViewById(R.id.btnCalculateDomestic)
        textViewDomesticResultLabel = view.findViewById(R.id.textViewDomesticResultLabel)
        textViewDomesticResult = view.findViewById(R.id.textViewDomesticResult)

        // Ajoutez un écouteur de clic au bouton pour déclencher le calcul
        btnCalculateDomestic.setOnClickListener {
            calculateAndDisplayResult()
            editTextElectricityConsumption.setText("")
            editTextGasConsumption.setText("")
        }

        return view
    }

    private fun calculateAndDisplayResult() {
        // Récupérez les valeurs des champs de saisie
        val electricityConsumption = editTextElectricityConsumption.text.toString().toDoubleOrNull() ?: 0.0
        val gasConsumption = editTextGasConsumption.text.toString().toDoubleOrNull() ?: 0.0

        // Appelez la fonction de calcul de l'empreinte carbone
        val carbonFootprint = calculateCarbonFootprint(electricityConsumption, gasConsumption)

        // Affichez le résultat dans le champ de résultat
        val formattedResult = DecimalFormat("#.##").format(carbonFootprint)
        textViewDomesticResult.text = "$formattedResult kg CO2"
    }

    private fun calculateCarbonFootprint(electricityConsumption: Double, gasConsumption: Double): Double {
        // Constants for emission factors (arbitrary values for illustration)
        val electricityEmissionFactor = 0.5
        val gasEmissionFactor = 1.8

        // Calculate carbon footprint
        val carbonFootprintElectricity = electricityConsumption * electricityEmissionFactor
        val carbonFootprintGas = gasConsumption * gasEmissionFactor

        // Total carbon footprint
        val totalCarbonFootprint = carbonFootprintElectricity + carbonFootprintGas

        // You can add additional logic or factors based on your requirements

        doAdd(totalCarbonFootprint,this@DomesticFragment.requireContext())
        return totalCarbonFootprint
    }


}

private fun doAdd(valeur: Double, context: Context) {
    val gson = Gson()

    ApiService.consommationService.add(
        ConsommationService.ConsommationBody(
            "Domestique",
            valeur
        )
    ).enqueue(
        object : Callback<ConsomationResponse> {
            override fun onResponse(
                call: Call<ConsomationResponse>,
                response: Response<ConsomationResponse>
            ) {
                if (response.isSuccessful) {
                    val token = gson.toJson(response.body())
                    val jsonObject = JSONTokener(token).nextValue() as JSONObject
                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()

                } else {
                    Log.d("HTTP ERROR", "status code is " + response.code())
                    Toast.makeText(context, "Please Check Your Information", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<ConsomationResponse>,
                t: Throwable
            ) {
                Log.d("FAIL", "fail server $t")
                Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
            }
        }
    )
}
