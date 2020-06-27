package com.miluweather.android.net

import com.miluweather.android.net.api.PlaceService
import com.miluweather.android.net.api.WeatherService

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  SendRequest
 * @Remark: 统一的网络数据源访问入口
 */
object SendRequest {

    /**
     * 城市api接口对象
     */
    private val placeService = ServiceCreator.create<PlaceService>()

    /**
     * 天气api接口对象
     */
    private val weatherService = ServiceCreator.create<WeatherService>()

    /**
     * 搜索城市接口
     * @param query 查询的字段
     */
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query)

    /**
     * 获取实时天气
     * @param lng 经度
     * @param lat 纬度
     */
    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat)

    /**
     * 获取未来几天天气
     * @param lng 经度
     * @param lat 纬度
     */
    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat)
}