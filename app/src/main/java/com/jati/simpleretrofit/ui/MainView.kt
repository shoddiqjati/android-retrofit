package com.jati.simpleretrofit.ui

import com.jati.simpleretrofit.model.WeatherData

/**
 * Created by jati on 10/03/18.
 */
interface MainView {
    fun showProgress(isLoading: Boolean)
    fun showWeather(listWeather: List<WeatherData>)
    fun showError(message: String)
}