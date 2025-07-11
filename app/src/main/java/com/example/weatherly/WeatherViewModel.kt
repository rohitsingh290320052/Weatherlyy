package com.example.weatherly

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherly.api.RetrofitInstance

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    fun getData(city : String){
        Log.i("city name:", city)

    }
}