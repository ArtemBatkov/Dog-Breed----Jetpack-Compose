package com.example.dogbreed.Services.DogBreedService


import android.content.Context
import com.example.dogbreed.Models.DogBreedModel
import com.example.dogbreed.Services.ClientService.ClientApi
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DogBreed() : DogBreedInterface{
    override suspend fun parseBreedList(s: String, context: Context) : List<DogBreedModel>?{
        val localBreedList = mutableListOf<DogBreedModel>()
        if(s.isEmpty()) return null;
        val jsonResponse = JSONObject(s)
        val jsonObjMessage = jsonResponse.getJSONObject("message")
        val status = jsonResponse.getString("status")
        if (!status.equals("success")) {
            throw Exception("VOLLEY DogBreed IS NOT SUCCESSFUL STATUS!")
        }

        //go through each key
        jsonObjMessage.keys().forEach { breedName ->
            val subBreedArray: JSONArray = jsonObjMessage.optJSONArray(breedName)
            val subBreedList: MutableList<String> = mutableListOf()
            if (subBreedArray != null) {
                val n = subBreedArray.length()
                for (i in 0 until n) {
                    val subBreedName = subBreedArray.getString(i)
                    subBreedList.add(subBreedName)
                }
            }

            //fetching an image
            val photo: String? = fetchImage(breedName, context)




            val model = DogBreedModel(breedName, subBreedList, photo ?: "")
            localBreedList.add(model)
        }

        return(localBreedList).toList()
    }

    override suspend fun fetchImage(dogname: String, context: Context): String? {
        return try {
            val basicUrl = "https://dog.ceo/api/breed/$dogname/images/random"

            val response = ClientApi(context).getJSONObject(basicUrl)
            val jsonResponse = JSONObject(response)
            val status = jsonResponse.getString("status")
            if (!status.equals("success")) {
                throw Exception("VOLLEY DogBreed IS NOT SUCCESSFUL STATUS when fetching an image!")
            }
            val message = jsonResponse.getString("message")
            message
        }catch(e: Exception){
            ""
        }
    }


}