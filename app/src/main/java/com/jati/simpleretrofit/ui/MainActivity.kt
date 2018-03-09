package com.jati.simpleretrofit.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.jati.simpleretrofit.R
import com.jati.simpleretrofit.model.WeatherData
import com.jati.simpleretrofit.network.ApiManager
import com.jati.simpleretrofit.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity(), MainView {

    private val weatherList = mutableListOf<WeatherData>()
    private val weatherAdapter by lazy {
        WeatherAdapter(weatherList)
    }
    private val apiManager = ApiManager()
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, apiManager, getString(R.string.open_weather_key))
        initRv()
        presenter.getRxWeather()
    }

    private fun initRv() {
        with(rv_data) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = weatherAdapter
        }
        sl_content.setOnRefreshListener(refresh)
    }

    override fun showProgress(isLoading: Boolean) {
        sl_content.isRefreshing = isLoading
    }

    override fun showWeather(listWeather: List<WeatherData>) {
        weatherList.clear()
        weatherList.addAll(listWeather)
        weatherAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        toast(message)
    }

    private val refresh = SwipeRefreshLayout.OnRefreshListener {
        presenter.getWeather()
    }

}

