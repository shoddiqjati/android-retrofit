package com.jati.simpleretrofit.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jati.simpleretrofit.R
import com.jati.simpleretrofit.model.WeatherData
import com.jati.simpleretrofit.utils.parseMillistToDate
import kotlinx.android.synthetic.main.item_weather.view.*

/**
 * Created by jati on 10/03/18.
 */
class WeatherAdapter(val data: List<WeatherData>) : RecyclerView.Adapter<WeatherAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bindData(data[position])
    }

    inner class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(weather: WeatherData) {
            itemView.tv_title.text = weather.weather.first().main
            itemView.tv_desc.text = weather.weather.first().desc
            itemView.tv_date.text = parseMillistToDate(weather.dateTime)
        }
    }
}