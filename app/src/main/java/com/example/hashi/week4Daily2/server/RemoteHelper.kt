package com.example.hashi.week4Daily2.server

import com.example.hashi.week4Daily2.APPID
import com.example.hashi.week4Daily2.BASE_WEATHER_URL
import com.example.hashi.week4Daily2.ZIP
import com.example.hashi.week4Daily2.model.WeatherData
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RemoteHelper {

    fun getWeatherData() : Single<WeatherData>{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_WEATHER_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RemoteService::class.java)

        return service.getWeatherData(ZIP, APPID)
    }

    /*fun getWeatherData() : Call<ResponseBody>{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_WEATHER_URL)
            .build()

        val service = retrofit.create(RemoteService::class.java)

        return service.getWeatherData(ZIP, APPID)
    }*/
}