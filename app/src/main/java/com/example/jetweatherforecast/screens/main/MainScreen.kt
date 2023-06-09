package com.example.jetweatherforecast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text


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
import com.example.jetweatherforecast.data.DataorException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.Weatheritem
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.HumidityWindPressureRow
import com.example.jetweatherforecast.widgets.SunsetSunRiseRow
import com.example.jetweatherforecast.widgets.WeatherAppBar
import com.example.jetweatherforecast.widgets.WeatherDetailRow
import com.example.jetweatherforecast.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
){
    Log.d("TAG", "MainScreen: $city ")
    val weatherData = produceState<DataorException<Weather,Boolean,Exception>>(
        initialValue = DataorException(loading = true) ){
        // where state is produced you
        value = mainViewModel.getWeatherData(city = city.toString())

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
            onAddActionClicked = {
                                 navController.navigate(WeatherScreens.SearchScreen.name)

            },
            elevation = 5.dp)
        {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }

    }) {
         MainContent(data = weather)
    }

    // inside here we check to see actually we get our mainview
}
@Composable
fun MainContent(data: Weather) {
    val weatheritem = data.list[0]
    val imageUrl = " https://openweathermap.org/img/wn/${weatheritem.weather[0].icon}.png"

    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = formatDate(weatheritem.dt),
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
              Text(text = formatDecimals(weatheritem.temp.day) + "º" ,
                  style = MaterialTheme.typography.h4,
              fontWeight = FontWeight.ExtraBold)
              Text(text = weatheritem.weather[0].main,
                  fontStyle = FontStyle.Italic)

          }

      }
        HumidityWindPressureRow(weather = data.list[0])
        Divider()
        SunsetSunRiseRow(weather = data.list[0])
        Text( "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold)

        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
             color = Color(0xFFEEF1EF),
                shape = RoundedCornerShape(size = 14.dp)
                 ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                        contentPadding = PaddingValues(1.dp)){
                items(items = data.list) { item: Weatheritem ->
                WeatherDetailRow(weather = item )

                }
            }
        }

    }

}