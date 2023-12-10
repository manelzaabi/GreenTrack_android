package tn.esprit.nascar.model


import com.google.gson.annotations.SerializedName


data class ConsomationResponse(
    @SerializedName("todos")
    val consommation: Consommation
)

data class Consommation(
    @SerializedName("type")
    var type: String?,
    @SerializedName("valeur")
    var valeur: Double?
)
