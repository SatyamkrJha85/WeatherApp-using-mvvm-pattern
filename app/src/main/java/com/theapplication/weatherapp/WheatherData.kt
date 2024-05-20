package com.jetpackcomposethe.taskmanager.Data

import androidx.compose.ui.graphics.Color
import com.theapplication.weatherapp.R
import com.theapplication.weatherapp.ui.theme.LightBlue
import com.theapplication.weatherapp.ui.theme.LightGreen
import com.theapplication.weatherapp.ui.theme.LightPurple


data class WheatherData(
    val color: Color,
    val time: String,
    val icon: Int,
    val temp: String

)

val wheatherlist = listOf(
    WheatherData(
        color = LightBlue,
        time = "12: 32 AM",
        icon = R.drawable.weathersun,
        temp = "55°"
    ),
    WheatherData(
        color = LightPurple,
        time = "1: 32 AM",
        icon = R.drawable.cloudy,
        temp = "41°"
    ),
    WheatherData(
        color = LightGreen,
        time = "6: 12 AM",
        icon = R.drawable.weatherrain,
        temp = "12°"
    ),
    WheatherData(
        color = LightBlue,
        time = "12: 32 AM",
        icon = R.drawable.weathersun,
        temp = "55°"
    ),
    WheatherData(
        color = LightPurple,
        time = "1: 32 AM",
        icon = R.drawable.cloudy,
        temp = "41°"
    ),
    WheatherData(
        color = LightGreen,
        time = "6: 12 AM",
        icon = R.drawable.weatherrain,
        temp = "12°"
    )
)