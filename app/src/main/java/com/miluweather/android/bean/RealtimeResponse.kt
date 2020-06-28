package com.miluweather.android.bean

import com.google.gson.annotations.SerializedName

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class: RealtimeResponse
 * @Remark: 实时天气
 */
data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        val skycon: String,
        val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)

}