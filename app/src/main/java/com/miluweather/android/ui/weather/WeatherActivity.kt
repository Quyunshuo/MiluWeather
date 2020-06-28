package com.miluweather.android.ui.weather

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miluweather.android.R
import com.miluweather.android.base.BaseActivity
import com.miluweather.android.bean.Place
import com.miluweather.android.bean.Weather
import com.miluweather.android.bean.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Author: QuYunShuo
 * @Time:   2020/6/26
 * @Class:  WeatherActivity
 * @Remark: 天气主界面
 */
class WeatherActivity : BaseActivity() {

    private val mViewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun getLayoutId(): Int = R.layout.activity_weather

    override fun initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
        initData()
        swipeRefresh.apply {
            setColorSchemeResources(R.color.theme_color)
            setOnRefreshListener { refreshWeather() }
            setProgressViewEndTarget(true, 280)
        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerClosed(drawerView: View) {
                // 滑动菜单关闭时 同时对输入法进行关闭
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerOpened(drawerView: View) {}
        })
        navBtn.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        mViewModel.weatherLiveData.observe(this, Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing = false
        })
        refreshWeather()
    }

    /**
     * 初始化接收的数据
     */
    private fun initData() {
        if (mViewModel.locationLng.isEmpty()) {
            mViewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (mViewModel.locationLat.isEmpty()) {
            mViewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (mViewModel.placeName.isEmpty()) {
            mViewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
    }

    /**
     * 刷新天气
     */
    private fun refreshWeather() {
        mViewModel.refreshWeather(mViewModel.locationLng, mViewModel.locationLat)
        swipeRefresh.isRefreshing = true
    }

    /**
     * 更改城市
     */
    fun changePlace(place: Place) {
        place.let {
            mViewModel.locationLng = it.location.lng
            mViewModel.locationLat = it.location.lat
            mViewModel.placeName = it.name
        }
        refreshWeather()
    }

    /**
     * 展示天气信息
     */
    private fun showWeatherInfo(weather: Weather) {
        placeName.text = mViewModel.placeName
        // 实时天气数据
        val realtime = weather.realtime
        // 未来天气数据
        val daily = weather.daily
        // 填充now.xml布局中数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view =
                LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }
}

