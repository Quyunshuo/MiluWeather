package com.miluweather.android.net

import com.miluweather.android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  ServiceCreator
 * @Remark: Retrofit动态代理对象获取封装
 */
object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val BODY by lazy(mode = LazyThreadSafetyMode.NONE) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val NONE by lazy(mode = LazyThreadSafetyMode.NONE) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val netClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(if (BuildConfig.DEBUG) BODY else NONE)
            .retryOnConnectionFailure(true)
            .build()
    }

    private val retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(netClient)
            .build()
    }

    /**
     * 获取service动态代理对象
     * @param serviceClass 接口Class对象
     */
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * 获取service动态代理对象
     * 范型实化
     */
    inline fun <reified T> create(): T = create(T::class.java)
}