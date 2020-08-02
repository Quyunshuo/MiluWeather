package com.miluweather.android

import android.app.Application
import android.content.Context
import com.miluweather.android.utils.MMKVUtils

/**
 * @Author: QuYunShuo
 * @Time:  2020/6/26
 * @Class: BaseApplication
 * @Remark:
 */
class BaseApplication : Application() {

    companion object {
        // 全局Context
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        // 初始化MMKV 使用MMKV替代SP
        MMKVUtils.init(context)
    }
}