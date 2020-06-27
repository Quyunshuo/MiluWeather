package com.miluweather.android.net

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.miluweather.android.model.Place
import kotlinx.coroutines.Dispatchers

/**
 * @Author: QuYunShuo
 * @Time: 2020/6/27
 * @Class: Repository
 * @Remark: 对请求数据封装
 */
object Repository {

    /**
     * 发起搜索请求并返回可观察的LiveData
     */
    fun searchPlaces(query: String): LiveData<Result<List<Place>>> {

        return liveData(Dispatchers.IO) {
            // 请求结果
            val result = try {
                // 进行请求
                val placeResponse = Network.searchPlace(query)
                if (placeResponse.status == "ok") Result.success(placeResponse.places)
                else Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
            } catch (e: Exception) {
                Result.failure<List<Place>>(e)
            }
            emit(result)
        }
    }
}