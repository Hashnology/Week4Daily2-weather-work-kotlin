package com.example.hashi.week4Daily2


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.hashi.week4Daily2.model.MyWeather
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(var myWeatherList: List<MyWeather>, val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    val TAG = "RecyclerViewAdapter"
    lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        context = viewGroup.context
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myWeatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myWeather = myWeatherList[position]

        holder.tvCityName.text = myWeather.cityName
        holder.tvWeather.text = myWeather.weather
        holder.tvTimeDate.text = myWeather.timeDate
        holder.tvTemperature.text = myWeather.temperature

        var imageId : Int = context.resources.getIdentifier(myWeather.icon, "drawable", context.getPackageName())
        Picasso.get()
            .load(imageId)
            .error(R.drawable.ic_sunny)
            .resize(100, 100)
            .centerCrop()
            .into(holder.ivIcon)
        Log.d(TAG, myWeather.icon)

        holder.itemView.setOnClickListener {
            listener.onItemClickedListener(position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCityName : TextView
        var tvTimeDate: TextView
        var tvWeather : TextView
        var tvTemperature : TextView
        var ivIcon : ImageView

        init {
             tvCityName  = itemView.findViewById(R.id.tvCityName)
             tvTimeDate = itemView.findViewById(R.id.tvTimeDate)
             tvWeather = itemView.findViewById(R.id.tvWeather)
             tvTemperature = itemView.findViewById(R.id.tvTemperature)
             ivIcon  = itemView.findViewById(R.id.ivIcon)
        }

    }

    interface OnItemClickListener
    {
        fun onItemClickedListener(position: Int)
    }
}