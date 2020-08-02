package com.miluweather.android.dao

import com.google.gson.Gson
import com.miluweather.android.constant.SpKey
import com.miluweather.android.bean.Place
import com.miluweather.android.utils.MMKVUtils

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/28
 * @Class:  PlaceDao
 * @Remark: 持久化数据存储已选中城市的数据
 */
object PlaceDao {

    /**
     * 保存当前选中的城市
     */
    fun savePlace(place: Place) = MMKVUtils.putString(SpKey.SELECTED_PLACE, Gson().toJson(place))

    /**
     * 获取保存的当前选中的城市
     */
    fun getSavePlace(): Place =
        Gson().fromJson(MMKVUtils.getString(SpKey.SELECTED_PLACE, ""), Place::class.java)

    /**
     * 判断是否有保存的选中城市
     */
    fun isPlaceSave() = MMKVUtils.contains(SpKey.SELECTED_PLACE)
}