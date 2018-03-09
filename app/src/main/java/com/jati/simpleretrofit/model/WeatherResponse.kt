package com.jati.simpleretrofit.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jati on 09/03/18.
 */
data class WeatherResponse(
        @SerializedName("list")
        val data: List<WeatherData>
)

data class WeatherData(
        @SerializedName("dt")
        val dateTime: Long,
        @SerializedName("weather")
        val weather: List<Weather>
)

data class Weather(
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String,
        @SerializedName("description")
        val desc: String
)