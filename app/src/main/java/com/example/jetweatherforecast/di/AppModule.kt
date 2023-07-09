package com.example.jetweatherforecast.di

import android.content.Context
import android.provider.SyncStateContract.Constants
import androidx.room.Room
import com.example.jetweatherforecast.data.WeatherDao
import com.example.jetweatherforecast.data.WeatherDatabase
import com.example.jetweatherforecast.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase) : WeatherDao
    = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context : Context) : WeatherDatabase
    = Room.databaseBuilder(
        context,
        WeatherDatabase ::class.java,
        "weather_database")
        .fallbackToDestructiveMigration()
        .build()
    @Provides
    @Singleton
    fun provideOpenWeatherApi() : WeatherApi{
        return Retrofit.Builder()
            .baseUrl(com.example.jetweatherforecast.utils.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)

    }
}