package com.example.jetweatherforecast.screens.main

import android.provider.ContactsContract.Data
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweatherforecast.data.DataorException
import com.example.jetweatherforecast.model.City
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainViewModel @Inject constructor(private val repository: WeatherRepository):

    ViewModel() {
         val data: MutableState<DataorException<WeatherObject, Boolean, Exception>>
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
    }


}













// We want to use view model to get that information of repos onto user interface