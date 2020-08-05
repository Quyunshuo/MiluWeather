package com.miluweather.android.ui.place

import android.util.Log
import androidx.lifecycle.*
import com.miluweather.android.bean.Place
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  PlaceViewModel
 * @Remark:
 */
class PlaceViewModel : ViewModel() {

    private val mRepository by lazy { PlaceRepository() }

    val placeList = ArrayList<Place>()

    // 私有
    private val _placeListLiveData = MutableLiveData<List<Place>>()

    // 对外暴露
    val placeListLiveData: LiveData<List<Place>> = _placeListLiveData

    /**
     * 搜索
     * @param query 搜索值
     */
    @ExperimentalCoroutinesApi
    fun searchPlaces(query: String) {
        viewModelScope.launch {
            mRepository.searchPlaces(query)
                .onStart {
                    Log.d("qqq", "onStart: 发起搜索请求")
                }
                .catch {
                    Log.d("qqq", "catch: 发生异常 Msg:${it}")
                }
                .onCompletion {
                    Log.d("qqq", "onCompletion: 请求完成")
                }
                .collectLatest {
                    _placeListLiveData.postValue(it.places)
                }
        }
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