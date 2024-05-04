package com.example.sunday.network

import com.example.sunday.models.HourlyForecast
import com.example.sunday.models.Location
import com.example.sunday.models.forecasts.DailyForecasts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "gd80c3llnaezbNTy3C0cseYg7BV8oiBA"

interface Api {
    @GET("locations/v1/cities/search")
    suspend fun searchLocation(
        @Query("apikey") apikey:String = API_KEY,
        @Query("q") query: String
    ): Response<List<Location>>

    @GET("forecasts/v1/daily/5day/{location_key}")
    suspend fun getDailyForecasts(
        @Path("location_key") locationKey:String,
        @Query("apikey") apikey:String = API_KEY,
        @Query("metric") metric:Boolean = true,
    ): Response<DailyForecasts>

    @GET("forecasts/v1/hourly/12hour/{location_key}")
    suspend fun getHourlyForecasts(
        @Path("location_key") locationKey:String,
        @Query("apikey") apikey:String = API_KEY,
        @Query("metric") metric:Boolean = true,
    ): Response<List<HourlyForecast>>
}