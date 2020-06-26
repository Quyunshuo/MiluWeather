package com.miluweather.android.ui.splash

import android.content.Intent
import com.miluweather.android.R
import com.miluweather.android.base.BaseActivity
import com.miluweather.android.ui.weather.WeatherActivity
import kotlinx.coroutines.*

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  SplashActivity
 * @Remark: 启动页
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initView() {
        mMainScope.launch(Dispatchers.Default) {
            delay(1000)
            startActivity(Intent(this@SplashActivity, WeatherActivity::class.java))
            delay(100)
            finish()
        }
    }
}
