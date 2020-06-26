package com.miluweather.android.utils

import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  SpUtils
 * @Remark: MMKV使用封装
 */
object SpUtils {

    /**
     * 初始化
     */
    fun init(context: Context) = MMKV.initialize(context)

    /**
     * 保存数据（简化）
     */
    fun put(key: String, value: Any) =
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Double -> putDouble(key, value)
            is Boolean -> putBoolean(key, value)
            else -> false
        }

    fun putString(key: String, value: String): Boolean = MMKV.defaultMMKV().encode(key, value)

    fun getString(key: String, def: String): String = MMKV.defaultMMKV().decodeString(key, def)

    fun putInt(key: String, value: Int): Boolean = MMKV.defaultMMKV().encode(key, value)

    fun getInt(key: String, def: Int): Int = MMKV.defaultMMKV().decodeInt(key, def)

    fun putLong(key: String, value: Long): Boolean = MMKV.defaultMMKV().encode(key, value)

    fun getLong(key: String, def: Long): Long = MMKV.defaultMMKV().decodeLong(key, def)

    fun putDouble(key: String, value: Double): Boolean = MMKV.defaultMMKV().encode(key, value)

    fun getDouble(key: String, def: Double): Double = MMKV.defaultMMKV().decodeDouble(key, def)

    fun putFloat(key: String, value: Float): Boolean = MMKV.defaultMMKV().encode(key, value)

    fun getFloat(key: String, def: Float): Float = MMKV.defaultMMKV().decodeFloat(key, def)

    fun putBoolean(key: String, value: Boolean): Boolean = MMKV.defaultMMKV().encode(key, value)

    fun getBoolean(key: String, def: Boolean): Boolean = MMKV.defaultMMKV().decodeBool(key, def)

    fun contains(key: String): Boolean = MMKV.defaultMMKV().contains(key)
}