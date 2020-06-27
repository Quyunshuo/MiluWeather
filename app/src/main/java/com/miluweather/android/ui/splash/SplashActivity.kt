package com.miluweather.android.ui.splash

import android.content.Intent
import com.miluweather.android.R
import com.miluweather.android.base.BaseActivity
import com.miluweather.android.ui.main.MainActivity
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
        mMainScope.launch(Dispatchers.IO) {
            delay(800)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            delay(100)
            finish()
        }
    }

    override fun onBackPressed() {}
}
