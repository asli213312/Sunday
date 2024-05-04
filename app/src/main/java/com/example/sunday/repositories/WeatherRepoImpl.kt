package com.example.sunday.repositories

import com.example.sunday.models.BaseModel
import com.example.sunday.models.HourlyForecast
import com.example.sunday.models.Location
import com.example.sunday.models.forecasts.DailyForecasts
import com.example.sunday.network.Api
import retrofit2.Response

class WeatherRepoImpl(private val api: Api): WeatherRepo {
    override suspend fun searchLocation(query: String): BaseModel<List<Location>> {
        return request {
            api.searchLocation(query = query)
        }
    }

    override suspend fun getDailyForecasts(locationKey: String): BaseModel<DailyForecasts> {
        return request {
            api.getDailyForecasts(locationKey = locationKey)
        }
    }

    override suspend fun getHourlyForecasts(locationKey: String): BaseModel<List<HourlyForecast>> {
        return request {
            api.getHourlyForecasts(locationKey = locationKey)
        }
    }
}

suspend fun<T> request(request: suspend () -> Response<T>): BaseModel<T>{
    try {
        request().also {
            return if (it.isSuccessful){
                BaseModel.Success(it.body()!!)
            } else {
                BaseModel.Error(it.errorBody()?.string().toString())
            }
        }
    } catch (e: Exception) {
        return BaseModel.Error(e.message.toString())
    }
}