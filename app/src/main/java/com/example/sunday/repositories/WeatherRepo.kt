package com.example.sunday.repositories

import com.example.sunday.models.BaseModel
import com.example.sunday.models.HourlyForecast
import com.example.sunday.models.Location
import com.example.sunday.models.forecasts.DailyForecasts

interface WeatherRepo {
    suspend fun searchLocation(query: String): BaseModel<List<Location>>
    suspend fun getDailyForecasts(locationKey: String): BaseModel<DailyForecasts>
    suspend fun getHourlyForecasts(locationKey: String): BaseModel<List<HourlyForecast>>
}