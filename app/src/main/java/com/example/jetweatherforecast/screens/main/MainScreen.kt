package com.example.jetweatherforecast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.jetweatherforecast.data.DataorException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()){
    Text(text = "Main Screen")

    

    val weatherData = produceState<DataorException<Weather,Boolean,Exception>>(
        initialValue = DataorException(loading = true) ){
        // where state is produced you
        value = mainViewModel.getWeatherData(city = "Seattle")

    }.value

    if(weatherData.loading == true){
        CircularProgressIndicator()
    }else if (weatherData.data != null) {
        
        MainScaffold(weather = weatherData.data!!, navController)


    }

}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather,navController: NavController) {
    
    Scaffold(topBar = {

        WeatherAppBar(title = weather.city.name + ",${weather.city.country}",

            navController= navController,
            elevation = 5.dp)
        {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }

    }) {
         MainContent(data = weather)
    }
  



    // inside here we check to see actually we get our mainview model

}

@Composable
fun MainContent(data: Weather) {
    val imageUrl = " https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"

    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = data.list[0].dt.toString(),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSecondary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(6.dp))


      Surface(modifier = Modifier
          .padding(4.dp)
          .size(200.dp),
          shape = CircleShape,
          color = Color(0xFFFFC400)
      ) {

          Column(verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally) {
              WeatherStateImage(imageUrl = imageUrl)

              // iMAGE
              Text(text = "56", style = MaterialTheme.typography.h4,
              fontWeight = FontWeight.ExtraBold
              )
              Text(text = "Snow", fontStyle = FontStyle.Italic)

          }

      }

    }

}

@Composable
fun WeatherStateImage(imageUrl: String) {

    Image(painter = rememberImagePainter(imageUrl)
        , contentDescription = " icon image" ,
    modifier = Modifier.size(80.dp))
    

}
