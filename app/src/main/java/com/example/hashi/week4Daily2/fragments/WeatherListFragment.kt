package com.example.hashi.week4Daily2.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hashi.week4Daily2.R

import com.example.hashi.week4Daily2.RecyclerViewAdapter
import com.example.hashi.week4Daily2.model.MyWeather
import com.example.hashi.week4Daily2.model.WeatherData
import com.example.hashi.week4Daily2.server.RemoteHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*

class WeatherListFragment : Fragment() {
    val TAG = "WeatherListFragment"

    lateinit var weatherList: List<MyWeather>
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: RecyclerViewAdapter

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weatherList = ArrayList()
        initWeather()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        layoutManager = LinearLayoutManager(context)

        return view
    }


    private fun initWeather() {

        RemoteHelper.getWeatherData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<WeatherData> {
                override fun onSuccess(t: WeatherData) {

                    Log.d(TAG, "${t.list.size}")
                    Log.d(TAG, "precipitation: ${t.list[25].modelRain._3h}mm")
                    for (i in 0 until t.list.size)
                    {
                        /*(weatherList as ArrayList<MyWeather>).add(MyWeather(t.city.name, t.list[i].dt_txt, t.list[i].weather[i].description,
                            "${String.format("%.2f", t.list[i].main.temp - 273.15)}°C", "$ICONURL${t.list[i].weather[i].icon}.png"))*/

                        for (j in 0 until t.list[i].weather.size) {
                            (weatherList as ArrayList<MyWeather>).add(
                                MyWeather(
                                    "${t.city.name}, ${t.city.country}",
                                    t.list[i].dt_txt,
                                    t.list[i].weather[j].description,
                                    "${String.format("%.2f", t.list[i].main.temp - 273.15)}°C",
                                    "_${t.list[i].weather[j].icon}",
                                    "Humidity: ${t.list[i].main.humidity}%",
                                    "Wind: ${String.format("%.2f", t.list[i].wind.speed)}m/s - ${String.format(
                                        "%.2f",
                                        t.list[i].wind.deg
                                    )}°",
                                    "Min Temperature: ${String.format("%.2f", t.list[i].main.temp_min - 273.15)}°C",
                                    "Max Temperature: ${String.format("%.2f", t.list[i].main.temp_max - 273.15)}°C",
                                    "Clouds: ${t.list[i].clouds.all}%"
                                )
                            )


                        }
                    }

                    Log.d(TAG, weatherList.toString())
                    adapter = RecyclerViewAdapter(
                        weatherList,
                        object : RecyclerViewAdapter.OnItemClickListener {
                            override fun onItemClickedListener(position: Int) {

                                //val precipitation = weatherList[position].precipitation
                                val humidity = weatherList[position].humidity
                                val wind = weatherList[position].wind
                                val minTemp = weatherList[position].minTemp
                                val maxTemp = weatherList[position].maxTemp
                                val clouds = weatherList[position].clouds

                                val weather = MyWeather(
                                    weatherList[position].cityName,
                                    weatherList[position].timeDate,
                                    weatherList[position].weather,
                                    weatherList[position].temperature,
                                    weatherList[position].icon,
                                    humidity, wind, minTemp, maxTemp, clouds
                                )
                                val args = Bundle()
                                args.putParcelable("weather", weather)

                                val detailFragment = WeatherDetailFragment()
                                val manager = fragmentManager
                                detailFragment.arguments = args
                                manager?.beginTransaction()?.replace(R.id.flFragment, detailFragment, "detailFragment")
                                    ?.addToBackStack("detailFragment")?.commit()

                                //Toast.makeText(context, "$weather Position: $position", Toast.LENGTH_SHORT).show()
                            }
                        })
                    rvWeather.layoutManager = layoutManager
                    rvWeather.adapter = adapter
                    adapter.notifyDataSetChanged()

                   /* Log.d(
                        TAG,
                        "Model_City name: ${t.city.name} Temperature: ${String.format(
                            "%.2f",
                            t.list[0].main.temp - 273.15
                        )}°C Humidity: ${t.list[0].main.humidity} Time: ${t.list[0].dt_txt}" +
                                " Icon: $ICONURL${t.list[0].weather[0].icon}.png " +
                                "Max/Min Temp: ${String.format(
                                    "%.2f",
                                    t.list[0].main.temp_max - 273.15
                                )}/${String.format(
                                    "%.2f",
                                    t.list[0].main.temp_min - 273.15
                                )}°C ${t.list[0].weather[0].description}"
                    )*/
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })

    }
}
