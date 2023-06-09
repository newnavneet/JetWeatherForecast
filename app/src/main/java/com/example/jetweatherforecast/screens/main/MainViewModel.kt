package com.example.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.data.DataorException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository):

    ViewModel() {
    val data: MutableState<DataorException<Weather, Boolean, Exception>>
            = mutableStateOf(DataorException(null,true,Exception("")))


    init {
        loadweather()
    }

    private fun loadweather(){
        getWeather("Seattle")
    }

    private fun getWeather(city: String){
        viewModelScope.launch {
            if (city.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)
            if(data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
        Log.d("GET", "getWeather:${data.value.data.toString()} ")
    }


}
