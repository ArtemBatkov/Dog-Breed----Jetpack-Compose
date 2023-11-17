package com.example.dogbreed.Services.ClientService

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.Queue
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ClientApi(private val context: Context): ClientApiInterface {
    val VOLLEY_ERROR = "VOLLEY_ERROR";
    private val queue = Volley.newRequestQueue(context)
    override suspend fun getJSONObject(url: String): String = suspendCoroutine<String>{ callback ->
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                    response -> callback.resume(response)
            },
            { error ->
                Log.e("ERROR", "VOLLEY ERROR: $error\t\tin url: $url")
                callback.resume(VOLLEY_ERROR)
            }
        )
        queue.add(stringRequest)
    }
}