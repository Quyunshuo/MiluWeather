package com.miluweather.android.ui.weather

import com.miluweather.android.base.BaseRepository
import com.miluweather.android.bean.Weather
import com.miluweather.android.net.SendRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  WeatherRepository
 * @Remark:
 */
class WeatherRepository : BaseRepository() {

    /**
     * 刷新天气数据
     * @param lng 经度
     * @param lat 纬度
     */
    suspend fun refreshWeather(lng: String, lat: String) = flow {
        // 两个并行请求
        coroutineScope {
            // 创建一个新的协程进行请求
            val deferredRealtime = async {
                SendRequest.getRealtimeWeather(lng, lat)
            }
            // 创建一个新的协程进行请求
            val deferredDaily = async {
                SendRequest.getDailyWeather(lng, lat)
            }
            // 对两个请求获取结果
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            // 对请求结果合并 并发射出去 如果有任何一个请求出现问题就抛出异常给调用者处理
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                emit(Weather(realtimeResponse.result.realtime, dailyResponse.result.daily))
            } else {
                throw  RuntimeException(
                    "realtime response status is ${realtimeResponse.status}" +
                            "daily response status is ${dailyResponse.status}"
                )
            }
        }
    }.flowOn(Dispatchers.IO)
}