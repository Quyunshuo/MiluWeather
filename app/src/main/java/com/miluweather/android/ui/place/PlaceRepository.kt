package com.miluweather.android.ui.place

import com.miluweather.android.base.BaseRepository
import com.miluweather.android.model.Place
import com.miluweather.android.net.SendRequest
import kotlinx.coroutines.Dispatchers

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  PlaceRepository
 * @Remark:
 */
class PlaceRepository : BaseRepository() {

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
}