package com.jati.simpleretrofit.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jati.simpleretrofit.model.WeatherData
import com.jati.simpleretrofit.model.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback


/**
 * Created by jati on 10/03/18.
 */
class ApiManager {
    private val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    var services: ApiServices

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        val httpClient = builder.build()
        val gson = Gson()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        services = retrofit.create(ApiServices::class.java)
    }

    //RxJava
    fun getRxWeather(hashMap: HashMap<String, String>) = services
            .getRxForecast(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    //Without RxJava
    fun getWeather(hashMap: HashMap<String, String>, callback: MyCallback<List<WeatherData>>) {
        services.getForecast(hashMap).enqueue(object : Callback<WeatherResponse>{
            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                t?.let {
                    it.printStackTrace()
                    callback.onError(it.localizedMessage)
                }

            }

            override fun onResponse(call: Call<WeatherResponse>?, response: retrofit2.Response<WeatherResponse>?) {
                response?.let {
                    if (it.isSuccessful) {
                        it.body()?.let {
                            callback.onSuccess(it.data)
                        }
                    } else {
                        callback.onError(it.code().toString())
                    }
                }
            }

        })
    }

    interface MyCallback<T> {
        fun onSuccess(response: T)
        fun onError(message: String)
    }
}