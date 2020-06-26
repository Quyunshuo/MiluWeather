package com.miluweather.android.net

import com.miluweather.android.constant.AppConstant
import com.miluweather.android.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  PlaceService
 * @Remark:
 */
interface PlaceService {

    /**
     * 搜索城市接口
     */
    @GET("v2/place?token=${AppConstant.TOKEN}&lang=zh_CN")
    suspend fun searchPlaces(@Query("query") query: String): PlaceResponse
}