package com.miluweather.android.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  BaseFragment
 * @Remark:
 */
abstract class BaseFragment : Fragment() {

    protected val mMainScope by lazy { MainScope() }

    protected lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        mMainScope.cancel()
    }
}