package com.example.dogbreed.Views



import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.dogbreed.Models.DogBreedModel

import com.example.dogbreed.ViewModels.DogBreedViewModel.DogBreedViewModel
import com.example.dogbreed.ui.theme.DogBreedTheme

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import coil.compose.rememberImagePainter
import com.example.dogbreed.R


class MainActivity : ComponentActivity() {
    private val dogViewModel by viewModels<DogBreedViewModel>()

    private lateinit var mainDogList: List<DogBreedModel>
    private lateinit var testBtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)



        //grab url
        var url = resources.getString(R.string.dog_breed_link)
        //getDogList
        dogViewModel.getDogList(url, this)

        setContent {
            DogBreedTheme {
                Surface (color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp)
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main_page"
                    ) {
                        composable("main_page") {
                            MainScreen(dogViewModel, navController)
                        }
                        composable("dog_detailed_page") {
                                val dog =  dogViewModel.lastSelectedDogFromList
                                if(dog!= null)
                                        DetailScreen(dog, dogViewModel)
                        }

                    }

                    //ACTUAL PAGE
                    //MainScreen(dogViewModel, navController)
                }
            }
        }
    }

    fun getContext(): MainActivity {
        return this
    }

}




@Preview(showBackground = true)
@Composable
fun CardPreview() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main_page"
    ) {
        composable("main_page") {
            MainScreen(tests, navController)
        }
        composable("dog_detailed_page") { navBackStackEntry->
        }
        // Add more destinations as needed
    }
    DogCardItem(dog = testDog, navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    MainScreen(tests, navController)
}





@Composable
fun MainScreen(dogViewModel: DogBreedViewModel, navHostController: NavHostController){
    DogBreedTheme {
        //val progress by dogViewModel.progressScore.collectAsState()
        val isLoading by dogViewModel.isLoad.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            if(isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(20.dp)
                        .weight(0.1f)
                )
            }

            DogRecyclerView(dogViewModel, modifier = Modifier
                .fillMaxSize()
                .weight(if (isLoading) 0.9f else 1f),
                navHostController = navHostController
            )
        }
    }
}

@Composable
fun MainScreen(list: List<DogBreedModel>,navHostController: NavHostController) {
    DogBreedTheme {
        //val progress: Float = 50f
        val isLoading = true
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            if(isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.1f)
                )
            }
            DogRecyclerView(list, modifier = Modifier
                .fillMaxSize()
                .weight(if (isLoading) 0.9f else 1f),
                navHostController = navHostController
            )
        }
    }
}


val testDog = DogBreedModel("SOBAKA", listOf("PORODA1","PORODA2","PORODA3"), "https://images.dog.ceo/breeds/hound-blood/n02088466_9242.jpg")
val tests = List<DogBreedModel>(30){ testDog }

@Composable
fun DogCardItem(dog: DogBreedModel, navController: NavHostController, dogViewModel: DogBreedViewModel? = null){
    val name = dog.breedName
    var builder = StringBuilder()

    val subbreed: String =   dog.subBreeds.joinToString(",\n")

    val dogimage = dog.breedImage

    var expanded by remember{ mutableStateOf(false)}
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .clickable {
                if (dogViewModel != null) {
                    dogViewModel.lastSelectedDogFromList = dog
                    navController.navigate("dog_detailed_page")
                } else {

                }
            }

    )
    {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ){

                    Icon(
                        painter = painterResource(id = if (!expanded) R.drawable.drop_down_icon else R.drawable.up_icon),
                        contentDescription = "icon_expand",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable { expanded = !expanded }
                    )
                    
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = name,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    val painter = rememberImagePainter(
                        data = if (dogimage.isNotBlank()) dogimage else R.drawable.default_image_dog,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.default_image_dog)
                            error(R.drawable.default_image_dog)
                        }
                    )

                    Image(
                        painter = painter,
                        contentDescription = "a dog image",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(100.dp)
                            .clip(CircleShape)
                            .aspectRatio(1.0f)
                    )
                }
            }

            if(expanded && subbreed.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = subbreed, textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun DogRecyclerView(dogViewModel: DogBreedViewModel, modifier: Modifier, navHostController: NavHostController){
    val dogList by dogViewModel.dogList.collectAsState()
    LazyColumn(modifier = modifier){
        itemsIndexed(items = dogList){ index, item ->
            DogCardItem(dog = item, navHostController, dogViewModel)
        }
    }
}



@Composable
fun DogRecyclerView(doglist: List<DogBreedModel>, modifier: Modifier, navHostController: NavHostController){
    LazyColumn(modifier = modifier){
        itemsIndexed(items = doglist){ index, item ->
            DogCardItem(dog = item, navHostController)
            key{
              index
            }
        }
    }
}








