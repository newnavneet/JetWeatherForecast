package com.example.jetweatherforecast.repository

import android.util.Log
import com.example.jetweatherforecast.data.DataorException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery:String)
    : DataorException<Weather,Boolean,Exception> {
        val response = try {
            // invoke the weather api which  acces to because of Di We have injected weather api which knows how to get data from retrofit
          api.getWeather(query = cityQuery)
        } catch (e: java.lang.Exception){
            Log.d("REX", "getWeather: $e")
            return DataorException(e=e)
        }
        Log.d("Inside", "getWeather: $response")
            return DataorException(data = response)

    }
}


//