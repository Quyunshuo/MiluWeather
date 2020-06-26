package com.miluweather.android.model

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  PlaceResponse
 * @Remark: 城市搜索结果
 */
data class PlaceResponse(val status: String, val places: List<Place>)