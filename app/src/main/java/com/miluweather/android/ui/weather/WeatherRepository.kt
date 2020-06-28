package com.miluweather.android.ui.weather

import com.miluweather.android.base.BaseRepository
import com.miluweather.android.bean.Weather
import com.miluweather.android.net.SendRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

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
    fun refreshWeather(lng: String, lat: String) =
        fire(Dispatchers.IO) {
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

                // 进一步合并处理结果
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    Result.success<Weather>(
                        Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    )
                } else {
                    Result.failure<Weather>(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
        }
}