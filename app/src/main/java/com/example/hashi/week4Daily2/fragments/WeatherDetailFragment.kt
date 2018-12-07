package com.example.hashi.week4Daily2.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hashi.week4Daily2.R


import com.example.hashi.week4Daily2.model.MyWeather
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*

class WeatherDetailFragment : Fragment() {
    private val TAG = "WeatherDetailFragment"

    companion object {
        fun newInstance() = WeatherDetailFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        return view
    }

    override fun onStart() {
        super.onStart()
        val args = arguments
        val weather : MyWeather = args!!.getParcelable("weather")

        tvCity.text = weather.cityName
        tvTemperature.text = weather.temperature
        tvTimeDate.text = weather.timeDate
        tvWeather.text = weather.weather
        //tvPrecipitation.text = "Precipitation: ${weather.}"
        tvHumidity.text = weather.humidity
        tvWind.text = weather.wind
        tvMinTemp.text = weather.minTemp
        tvMaxTemp.text = weather.maxTemp
        tvClouds.text = weather.clouds


        var imageId : Int = context?.resources!!.getIdentifier(weather.icon, "drawable", context?.packageName)
        Picasso.get()
            .load(imageId)
            .error(R.drawable.ic_sunny)
            .resize(100, 100)
            .centerCrop()
            .into(ivIcon)

        Log.d(TAG, weather.toString())
    }
}
