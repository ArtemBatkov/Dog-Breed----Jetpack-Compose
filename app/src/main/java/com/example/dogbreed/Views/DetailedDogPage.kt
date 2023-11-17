package com.example.dogbreed.Views

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.dogbreed.Models.DogBreedModel
import com.example.dogbreed.ViewModels.DogBreedViewModel.DogBreedViewModel
import com.example.dogbreed.Views.ui.theme.DetailedDogPageTheme
import com.example.dogbreed.Views.ui.theme.DogBreedTheme
import com.example.dogbreed.Views.ui.theme.detailedPageColor
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun DetailScreen(dog: DogBreedModel, dogViewModel: DogBreedViewModel) {
    DetailedDogPageTheme {
        val name = dog.breedName

        //val newName by rememberUpdatedState(newValue = "${dog.breedName} ${dog.breedName}")

        val index  by rememberUpdatedState  (newValue  = dogViewModel.getIndexByName(name))
        val newName = "${dog.breedName} ${dog.breedName}"

        if(index != -1){
            val newDog = dog.copy()
            newDog.breedName = newName
            dogViewModel.updateDogWithIndex(index, newDog)
        }


        val modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
        Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center){
            Text(
                text = if(index != -1) newName else "Dog Is NOT available now!",
                modifier = Modifier.wrapContentHeight().fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailedPreview(@PreviewParameter(DogBreedModelPreviewProvider::class) dog: DogBreedModel) {
    DetailScreen(dog, null!!)
}

class DogBreedModelPreviewProvider : PreviewParameterProvider<DogBreedModel> {
    override val values: Sequence<DogBreedModel> = sequenceOf(
        DogBreedModel("Labrador", listOf("Golden"), "https://example.com/labrador.jpg"),
        DogBreedModel("Bulldog", emptyList(), "https://example.com/bulldog.jpg"),)
}