package com.example.hashi.week4Daily2.model

data class WeatherData(val cod: String, val message: Double, val cnt: Int, val list: List<WeatherList>, val city: Model_City)