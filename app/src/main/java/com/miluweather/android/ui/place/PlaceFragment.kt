package com.miluweather.android.ui.place

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.miluweather.android.R
import com.miluweather.android.adapter.PlaceAdapter
import com.miluweather.android.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/27
 * @Class:  PlaceFragment
 * @Remark: 搜索城市页面
 */
class PlaceFragment : BaseFragment() {

    private val mViewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    private lateinit var mAdapter: PlaceAdapter

    override fun getLayoutId(): Int = R.layout.fragment_place

    override fun initView() {
        mAdapter = PlaceAdapter(mViewModel.placeList)
        recyclerView.layoutManager = LinearLayoutManager(mContext)
        recyclerView.adapter = mAdapter

        // 输入框监听
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                mViewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                mViewModel.placeList.clear()
                mAdapter.notifyDataSetChanged()
            }
        }

        // 观察请求结果
        mViewModel.placeLivaData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                // 将城市列表清空并设置新的请求结果
                mViewModel.placeList.clear()
                mViewModel.placeList.addAll(places)
                mAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(mContext, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}