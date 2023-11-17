package com.example.dogbreed.ViewModels.DogBreedViewModel

import android.content.Context
import androidx.compose.runtime. *
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreed.Models.DogBreedModel
import com.example.dogbreed.Services.ClientService.ClientApi
import com.example.dogbreed.Services.DogBreedService.DogBreed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogBreedViewModel: ViewModel() {
    private var _lastSelectedDogFromList: DogBreedModel? = null
    var lastSelectedDogFromList: DogBreedModel?
        get() = _lastSelectedDogFromList
        set(value){
            _lastSelectedDogFromList = value
        }

    private var _progressScore = MutableStateFlow<Float>(0f)
    val progressScore: MutableStateFlow<Float>
        get() = _progressScore

    private var _isLoad = MutableStateFlow<Boolean>(false)
    val isLoad: MutableStateFlow<Boolean>
        get() = _isLoad


    private var _dogListResponse = MutableStateFlow<List<DogBreedModel>>(emptyList())
    val dogList: MutableStateFlow<List<DogBreedModel>> get() {
        if(_dogListResponse.value.isNotEmpty()){
            _isLoad.value = false
        }
        return _dogListResponse
    }

    fun getDogList(url: String, context: Context){
        viewModelScope.launch {
            //get list of dogs
            _isLoad.value = true
            _progressScore.value = 5f
            val response = ClientApi(context).getJSONObject(url)
            _progressScore.value += 30f
            val listOfDogs = DogBreed().parseBreedList(response, context);
            _progressScore.value += 60f
            _isLoad.value = false
            if(listOfDogs != null) {
                _dogListResponse.value = listOfDogs!!
                _progressScore.value += 5f
            }
        }
    }

   fun getIndexByName(dogname: String) : Int{
       return dogList.value.indexOfFirst{it.breedName == dogname}
   }
    fun updateDogWithIndex(index: Int, dog: DogBreedModel){
        val tempList = dogList.value.toMutableList()
        tempList[index] = dog
        dogList.value = tempList
    }
}

