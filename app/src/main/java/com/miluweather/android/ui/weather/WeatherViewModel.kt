package com.miluweather.android.ui.weather

import android.util.Log
import androidx.lifecycle.*
import com.miluweather.android.bean.Weather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  WeatherViewModel
 * @Remark:
 */
class WeatherViewModel : ViewModel() {

    private val mRepository by lazy { WeatherRepository() }

    // 私有
    private val _weatherLiveData = MutableLiveData<Weather?>()

    // 对外暴露
    val weatherLiveData: LiveData<Weather?> = _weatherLiveData

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    @ExperimentalCoroutinesApi
    fun refreshWeather(lng: String, lat: String) {
        viewModelScope.launch {
            mRepository.refreshWeather(lng, lat)
                .onStart {
                    Log.d("qqq", "onStart: 发起搜索请求")
                }
                .catch {
                    Log.d("qqq", "catch: 发生异常 Msg:${it}")
                    _weatherLiveData.postValue(null)
                }
                .onCompletion {
                    Log.d("qqq", "onCompletion: 请求完成")
                }
                .collectLatest {
                    _weatherLiveData.postValue(it)
                }
        }
    }
}