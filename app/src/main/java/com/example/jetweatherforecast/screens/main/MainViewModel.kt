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
        suspend fun getWeatherData(city:String)
        : DataorException<Weather,Boolean,Exception>{
            return repository.getWeather(cityQuery = city)

        }





    }
