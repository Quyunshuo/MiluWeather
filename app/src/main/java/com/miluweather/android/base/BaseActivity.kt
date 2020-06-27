package com.miluweather.android.base

import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
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

    /**
     * 设置状态栏的颜色
     *@param color 色值
     */
    fun setStatusBarColor(@ColorInt color: Int, statusBarAlpha: Int = 0) {
        StatusBarUtil.setColor(this, color, statusBarAlpha)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMainScope.cancel()
    }
}