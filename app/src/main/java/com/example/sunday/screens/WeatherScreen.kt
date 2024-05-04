package com.example.sunday.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.sharp.ArrowDownward
import androidx.compose.material.icons.sharp.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sunday.models.BaseModel
import com.example.sunday.ui.theme.Dark200
import com.example.sunday.ui.theme.russoFont
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun WeatherScreen(
    navController: NavController,
    locationKey: String,
    locationName: String,
    country: String,
    viewModel: WeatherViewModel = viewModel()
) {
    val dailyForecasts by viewModel.dailyForecast.collectAsState()
    val hourlyForecast by viewModel.hourlyForecast.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDailyForecast(locationKey)
        viewModel.getHourlyForecast(locationKey)
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.padding(vertical = 32.dp)) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                imageVector = Icons.Default.KeyboardDoubleArrowLeft,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = locationName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Text(text = country, color = Color.Gray)
            }
        }
        AnimatedVisibility(visible = hourlyForecast is BaseModel.Success) {
            val data = hourlyForecast as BaseModel.Success
            val temp = data.data.first().temperature
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${temp.value}°",
                    fontWeight = FontWeight.Bold,
                    fontSize = 80.sp,
                    color = Color.White,
                    fontFamily = russoFont
                )
            }
        }
        AnimatedVisibility(visible = hourlyForecast is BaseModel.Loading) {
            Loading()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Hourly Forecasts:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility(visible = hourlyForecast is BaseModel.Success) {
            val data = hourlyForecast as BaseModel.Success
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(data.data) {forecast ->
                    Column(
                        modifier = Modifier
                            .size(100.dp, 140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Dark200),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = SimpleDateFormat("H a")
                                    .format(Date(forecast.epochDateTime * 1000)),
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        AsyncImage(
                            modifier = Modifier.size(70.dp),
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data("http://developer.accuweather.com/sites/default/files/" +
                                        "${forecast.weatherIcon.fixIcon()}-s.png").build(),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = forecast.temperature.value.toString() + "°", color = Color.White)
                    }
                }
            }
        }
        AnimatedVisibility(visible = hourlyForecast is BaseModel.Loading) {
            Loading()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Daily Forecasts:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility(visible = dailyForecasts is BaseModel.Success) {
            val data = dailyForecasts as BaseModel.Success
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data.data.dailyForecasts) {forecast ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Dark200)
                            .padding(start = 16.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "${SimpleDateFormat("d").format(Date(forecast.epochDate*1000))}th",
                            color = Color.White
                        )
                        Row {
                            Icon(
                                Icons.Sharp.ArrowDownward,
                                tint = Color(0XFFFF5353),
                                contentDescription = null
                            )
                            Text(text = "${forecast.temperature.min.value}°", color = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                Icons.Sharp.ArrowUpward,
                                tint = Color(0XFF2EFF8C),
                                contentDescription = null
                            )
                            Text(text = "${forecast.temperature.max.value}°", color = Color.White)
                        }
                        AsyncImage(
                            modifier = Modifier.size(70.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("http://developer.accuweather.com/sites/default/files/${forecast.day.icon.fixIcon()}-s.png")
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
        AnimatedVisibility(visible = dailyForecasts is BaseModel.Loading) {
            Loading()
        }
    }
}

fun Int.fixIcon(): String {
    return if (this > 9) {
        Log.d("MY_TAG", "Returned weather icon: ${this.toString()}")
        toString()
    } else {
        "0${this}"
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.White)
    }
}