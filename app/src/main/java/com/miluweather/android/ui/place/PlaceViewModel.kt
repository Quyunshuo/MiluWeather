package com.miluweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.miluweather.android.bean.Place

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  PlaceViewModel
 * @Remark:
 */
class PlaceViewModel : ViewModel() {

    private val mRepository by lazy { PlaceRepository() }

    // 可观察的搜索值
    private val searchLivaData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    // 当搜索值发生变化也就是调用searchPlaces()方法时
    // 会触发Transformations.switchMap()的逻辑进行网络请求 返回一个可观察的对象
    val placeLivaData =
        Transformations.switchMap(searchLivaData) { mRepository.searchPlaces(it) }

    /**
     * 搜索
     * @param query 搜索值
     */
    fun searchPlaces(query: String) {
        searchLivaData.value = query
    }

    /**
     * 保存当前选中的城市
     */
    fun savePlace(place: Place) = mRepository.savePlace(place)

    /**
     * 获取保存的当前选中的城市
     */
    fun getSavePlace(): Place = mRepository.getSavePlace()

    /**
     * 判断是否有保存的选中城市
     */
    fun isPlaceSave() = mRepository.isPlaceSave()
}