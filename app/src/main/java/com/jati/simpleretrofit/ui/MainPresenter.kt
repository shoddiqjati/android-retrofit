package com.jati.simpleretrofit.ui

import com.jati.simpleretrofit.model.WeatherData
import com.jati.simpleretrofit.network.ApiManager
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jati on 10/03/18.
 */
class MainPresenter(val view: MainView, val apiManager: ApiManager, apiKey: String) {
    val hashMap by lazy {
        HashMap<String, String>()
    }

    val compositeDisposable = CompositeDisposable()

    init {
        hashMap["q"] = "Yogyakarta"
        hashMap["mode"] = "json"
        hashMap["units"] = "metric"
        hashMap["cnt"] = "15"
        hashMap["appid"] = apiKey
    }

    fun getWeather() {
        view.showProgress(true)
        apiManager.getWeather(hashMap, object : ApiManager.MyCallback<List<WeatherData>> {
            override fun onSuccess(response: List<WeatherData>) {
                view.showProgress(false)
                view.showWeather(response)
            }

            override fun onError(message: String) {
                view.showProgress(false)
                view.showError(message)
            }
        })
    }

    fun getRxWeather() {
        view.showProgress(true)
        compositeDisposable.add(apiManager.getRxWeather(hashMap)
                .subscribe({
                    view.showProgress(false)
                    it.data.let { view.showWeather(it) }
                }, {
                    view.showProgress(false)
                    it.printStackTrace()
                    view.showError(it.localizedMessage)
                }))
    }
}