package com.example.sunday.models

import com.google.gson.annotations.SerializedName

data class DailyForecast(
    @SerializedName("Date") val date:String,
    @SerializedName("EpochDate") val epochDate:Long,
    @SerializedName("Temp") val temperature:Temperature,
    @SerializedName("Day") val day:WeatherStat,
    @SerializedName("Night") val night:WeatherStat,
)

data class Temperature(
    @SerializedName("Minimum") val min:Value,
    @SerializedName("Maximum") val max:Value,
)

data class WeatherStat(
    @SerializedName("Icon") val icon:Int,
    @SerializedName("IconPhrase") val iconPhrase:String,
    @SerializedName("HasPrecipitation") val hasPrecipitation:Boolean,
)

