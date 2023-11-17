package com.example.dogbreed.Services.DogBreedService

import android.content.Context
import com.example.dogbreed.Models.DogBreedModel

interface DogBreedInterface {
    suspend fun parseBreedList(s: String, context: Context): List<DogBreedModel>?
    suspend fun fetchImage(dogname: String, context: Context): String?
}