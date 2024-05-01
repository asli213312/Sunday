package com.example.sunday.models.forecasts

import com.example.sunday.models.DailyForecast
import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("DailyForecasts") val dailyForecasts: List<DailyForecast>
)