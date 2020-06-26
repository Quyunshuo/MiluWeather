package com.miluweather.android.net

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  Network
 * @Remark: 统一的网络数据源访问入口
 */
object Network {

    /**
     * 城市api接口对象
     */
    private val placeService = ServiceCreator.create<PlaceService>()

    /**
     * 搜索城市
     * 挂起函数
     */
    suspend fun searchPlace(query: String) = placeService.searchPlaces(query)
}