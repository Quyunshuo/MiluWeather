package com.miluweather.android.net

import androidx.lifecycle.liveData
import com.miluweather.android.model.Place
import com.miluweather.android.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * @Author: QuYunShuo
 * @Time: 2020/6/27
 * @Class: Repository
 * @Remark: 对请求数据封装
 */
object Repository {

    /**
     * 发起搜索请求并返回可观察的LiveData
     * @param query 查询的字段
     */
    fun searchPlaces(query: String) =
        fire(Dispatchers.IO) {
            // 进行请求
            val placeResponse = SendRequest.searchPlaces(query)
            if (placeResponse.status == "ok") Result.success(placeResponse.places)
            else Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
        }

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

    /**
     * 对请求处理封装
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>> {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}