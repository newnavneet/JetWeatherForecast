package com.example.jetweatherforecast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    Text(text = data.city.name)

}
