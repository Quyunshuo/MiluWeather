package com.miluweather.android.base

import androidx.lifecycle.liveData
import kotlin.coroutines.CoroutineContext

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  BaseRepository
 * @Remark: Model层Repository基类
 */
open class BaseRepository {

    /**
     * 对请求处理封装
     * 设置liveData开启的协程在页面销毁后立即进行销毁
     * 这个LiveData确实比较特殊，它是经过了switchMap之后转换了一次，和界面上观察的LiveData理论上确实不是同一个
     * switchMap内部的MediatorLiveData会持有对liveData函数返回的LiveData对象的观察者引用
     * 如果界面关闭的话，这个持有的观察者引用会被remove，然后会触发LiveData的cancel逻辑
     * 但是在cancel之前会先delay默认的秒数，也就是5秒
     * 这个秒数可以自定义
     */
    protected fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(timeoutInMs = 0L) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}