package com.example.dogbreed

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.dogbreed.ViewModels.DogBreedViewModel.DogBreedViewModel
import com.example.dogbreed.Views.MainActivity
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.runners.JUnit4



class DogBreedUnitTesting {



        private lateinit var dogViewModel: DogBreedViewModel
        private lateinit var mainActivity: MainActivity
        private lateinit var dogFetchLink: String
        private lateinit var context: Context


        init{
            mainActivity = MainActivity()
            context =  mainActivity.getContext()
            dogFetchLink = "https://dog.ceo/api/breeds/list/all"
            dogViewModel = DogBreedViewModel()
        }


@Test
fun t(){
    assertTrue("try",true)
}
/*
    @Test
    fun TestFetchingAllDogsList() {
        dogViewModel.getDogList(dogFetchLink, context)
        val listofDogs = dogViewModel.dogList
        val n = listofDogs.value.size
        assertTrue(n > 0 && listofDogs != null)
    }

*/
}