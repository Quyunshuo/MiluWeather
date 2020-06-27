package com.miluweather.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  BaseActivity
 * @Remark:
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val mMainScope by lazy { MainScope() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        mMainScope.cancel()
    }
}