package com.jati.simpleretrofit.network

import com.jati.simpleretrofit.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by jati on 10/03/18.
 */
interface ApiServices {
    @GET("forecast/daily")
    fun getRxForecast(@QueryMap hashMap: HashMap<String, String>): Observable<WeatherResponse>

    @GET("forecast/daily")
    fun getForecast(@QueryMap hashMap: HashMap<String, String>): Call<WeatherResponse>
}