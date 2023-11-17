package com.example.dogbreed.Models

import android.os.Parcel
import kotlinx.coroutines.flow.MutableStateFlow

//This is a dog breed model


data class DogBreedModel(
    var breedName: String,
    val subBreeds: List<String>,
    val breedImage: String
)
