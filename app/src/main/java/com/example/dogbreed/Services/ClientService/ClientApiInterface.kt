package com.example.dogbreed.Services.ClientService

import org.json.JSONObject

interface ClientApiInterface {
    suspend fun getJSONObject(url: String) : String;
}