package com.example.sunday.models.forecasts

import com.example.sunday.models.DailyForecast
import com.google.gson.annotations.SerializedName

data class DailyForecasts(
    @SerializedName("DailyForecasts") val dailyForecasts: List<DailyForecast>
)