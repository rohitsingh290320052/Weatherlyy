package com.example.weatherly.api

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Weatherapi {

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apikey: String,
        @Query("q") city : String

    ) : Response<WeatherModel>
}