package com.miluweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.miluweather.android.bean.Location

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  WeatherViewModel
 * @Remark:
 */
class WeatherViewModel : ViewModel() {

    private val mRepository by lazy { WeatherRepository() }

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        mRepository.refreshWeather(it.lng, it.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }
}