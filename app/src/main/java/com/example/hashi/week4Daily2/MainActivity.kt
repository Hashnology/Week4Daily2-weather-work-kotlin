package com.example.hashi.week4Daily2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hashi.week4Daily2.fragments.WeatherDetailFragment
import com.example.hashi.week4Daily2.fragments.WeatherListFragment
import com.example.hashi.week4Daily2.model.WeatherData
import com.example.hashi.week4Daily2.server.RemoteHelper

import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listFragement = WeatherListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.flFragment, listFragement, "listFragment").commit()

        val detailFragment = WeatherDetailFragment.newInstance()
        //supportFragmentManager.beginTransaction().add(R.id.frag2, detailFragment, "detailFragment").commit()

       //initWeather()
    }

    private fun initWeather()
    {

        RemoteHelper.getWeatherData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<WeatherData>{
                override fun onSuccess(t: WeatherData) {
                    Log.d(TAG, "Model_City name: ${t.city.name} Temperature: ${String.format("%.2f", t.list[0].main.temp - 273.15)}°C Humidity: ${t.list[0].main.humidity} Time: ${t.list[0].dt_txt}" +
                            " Icon: $ICONURL${t.list[0].weather[0].icon}.png " +
                            "Max/Min Temp: ${String.format("%.2f", t.list[0].main.temp_max- 273.15)}/${String.format("%.2f", t.list[0].main.temp_min- 273.15)}°C ${t.list[0].weather[0].description}")
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })

         /*RemoteHelper.getWeatherData().enqueue(object : retrofit2.Callback<ResponseBody>{
                   override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                       t.printStackTrace()
                   }

                   override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                       val gson = Gson( )
                       val json = response.body()?.string()
                       val data = gson.fromJson(json, WeatherData::class.java)
                       Log.d(TAG, "Model_City Name: ${data.city.name} Data Size: ${data.list[0].weather.size}")

                   }

               })*/
    }

}
