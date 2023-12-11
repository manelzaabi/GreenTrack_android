package tn.esprit.event_pdm

import okhttp3.MediaType
import okhttp3.RequestBody

fun String?.toRequestBody(mediaType: MediaType?): RequestBody {
    return RequestBody.create(mediaType!!, this!!)
}