package com.miluweather.android.ui.place

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.miluweather.android.R
import com.miluweather.android.adapter.PlaceAdapter
import com.miluweather.android.base.BaseFragment
import com.miluweather.android.model.Place
import com.miluweather.android.ui.main.MainActivity
import com.miluweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*
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
        if (activity is MainActivity) {
            (activity as MainActivity).setStatusBarColor(resources.getColor(R.color.theme_color))
        }
        isSelectedPlace()
        mAdapter = PlaceAdapter(mViewModel.placeList)
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val data = adapter.getItem(position) as Place
            // 对当前城市信息做保存
            mViewModel.savePlace(data)
            // 页面寄存于WeatherActivity的逻辑
            if (activity is WeatherActivity) {
                (activity as WeatherActivity).apply {
                    drawerLayout.closeDrawers()
                    changePlace(data)
                }
            } else {
                val intent = Intent(mContext, WeatherActivity::class.java).apply {
                    putExtra("location_lng", data.location.lng)
                    putExtra("location_lat", data.location.lat)
                    putExtra("place_name", data.name)
                }
                startActivity(intent)
                activity?.finish()
            }
        }
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
        mViewModel.placeLivaData.observe(this, Observer {
            val places = it.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                // 将城市列表清空并设置新的请求结果
                mViewModel.placeList.clear()
                mViewModel.placeList.addAll(places)
                mAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(mContext, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    /**
     * 判断是否已经选中过城市
     * 如果已有保存的城市信息就直接进行跳转
     */
    private fun isSelectedPlace() {
        if (activity is MainActivity && mViewModel.isPlaceSave()) {
            val data = mViewModel.getSavePlace()
            val intent = Intent(mContext, WeatherActivity::class.java).apply {
                putExtra("location_lng", data.location.lng)
                putExtra("location_lat", data.location.lat)
                putExtra("place_name", data.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
    }
}