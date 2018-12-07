package com.example.hashi.week4Daily2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyWeather(val cityName: String, val timeDate: String, val weather: String, val temperature: String, val icon: String,
                     val humidity: String, val wind: String, val minTemp: String, val maxTemp: String,
                     val clouds: String): Parcelable