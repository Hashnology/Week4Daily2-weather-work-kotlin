package com.example.hashi.week4Daily2.model

data class WeatherList(val dt: Int, val main: Main, val weather: List<Weather>, val clouds: Clouds, val wind: Wind, val modelRain: ModelRain, val sys: Sys, val dt_txt: String)
