package com.example.jetweatherforecast.repository

import com.example.jetweatherforecast.data.DataorException
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery:String)
    : DataorException<WeatherObject,Boolean,Exception> {
        val response = try {
            // invoke the weather api which  acces to because of Di We have injected weather api which knows how to get data from retrofit
          api.getWeather(query = cityQuery)
        } catch (e: java.lang.Exception){

            return DataorException(e=e)
        }
            return DataorException(data = response)



    }
}


//