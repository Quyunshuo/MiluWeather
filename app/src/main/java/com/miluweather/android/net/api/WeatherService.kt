package com.miluweather.android.net.api

import com.miluweather.android.constant.AppConstant
import com.miluweather.android.bean.DailyResponse
import com.miluweather.android.bean.RealtimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  WeatherService
 * @Remark: 天气请求接口
 */
interface WeatherService {

    /**
     * 获取实时天气
     * @param lng 经度
     * @param lat 纬度
     */
    @GET("v2.5/${AppConstant.TOKEN}/{lng},{lat}/realtime.json")
    suspend fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): RealtimeResponse

    /**
     * 获取未来几天天气
     * @param lng 经度
     * @param lat 纬度
     */
    @GET("v2.5/${AppConstant.TOKEN}/{lng},{lat}/daily.json")
    suspend fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): DailyResponse
}