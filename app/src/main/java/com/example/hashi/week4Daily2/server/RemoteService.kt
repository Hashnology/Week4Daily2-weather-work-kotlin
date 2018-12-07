package com.example.hashi.week4Daily2.server

import com.example.hashi.week4Daily2.model.WeatherData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("data/2.5/forecast")

    fun getWeatherData(@Query("zip") zip: String, @Query("appid") appid: String): Single<WeatherData>

    //fun getWeatherData(@Query("zip") zip: String, @Query("appid") appid: String): Call<ResponseBody>
}