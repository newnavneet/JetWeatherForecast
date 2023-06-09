package com.example.jetweatherforecast.screens.main

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataorException
import com.example.jetweatherforecast.model.Weather

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel){
    Text(text = "Main Screen")

    ShowData(mainViewModel)
}

@Composable
fun ShowData(mainViewModel: MainViewModel) {

    val weatherData = produceState<DataorException<Weather,Boolean,Exception>>(
        initialValue = DataorException(loading = true) ){
        // where state is produced you
        value = mainViewModel.data.value

    }.value

    if(weatherData.loading == true){
        CircularProgressIndicator()
    }else if (weatherData.data != null) {

        Text(text = "Main Screen ${weatherData.data!!.city.country}")
    }



    // inside here we check to see actually we get our mainview model

}