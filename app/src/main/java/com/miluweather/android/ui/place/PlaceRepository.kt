package com.miluweather.android.ui.place

import com.miluweather.android.base.BaseRepository
import com.miluweather.android.dao.PlaceDao
import com.miluweather.android.bean.Place
import com.miluweather.android.net.SendRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  PlaceRepository
 * @Remark:
 */
class PlaceRepository : BaseRepository() {

    /**
     * 使用 Flow 发起搜索请求并返回 flow
     * @param query flow
     */
    suspend fun searchPlaces(query: String) = flow {
        emit(SendRequest.searchPlaces(query))
    }.flowOn(Dispatchers.IO)

    /**
     * 保存当前选中的城市
     */
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    /**
     * 获取保存的当前选中的城市
     */
    fun getSavePlace(): Place = PlaceDao.getSavePlace()

    /**
     * 判断是否有保存的选中城市
     */
    fun isPlaceSave() = PlaceDao.isPlaceSave()
}