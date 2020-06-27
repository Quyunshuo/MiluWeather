package com.miluweather.android.model

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  Weather
 * @Remark: 天气数据整合
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)