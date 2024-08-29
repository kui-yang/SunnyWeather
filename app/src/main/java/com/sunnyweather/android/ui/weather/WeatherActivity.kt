package com.sunnyweather.android.ui.weather

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.android.R
import com.sunnyweather.android.databinding.ActivityWeatherBinding
import com.sunnyweather.android.databinding.ForecastBinding
import com.sunnyweather.android.databinding.LifeIndexBinding
import com.sunnyweather.android.databinding.NowBinding
import com.sunnyweather.android.logic.model.Weather
import com.sunnyweather.android.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }
    private lateinit var weatherBinding: ActivityWeatherBinding
    private lateinit var nowBinding: NowBinding
    private lateinit var lifeIndexBinding: LifeIndexBinding
    private lateinit var forecastBinding: ForecastBinding
    private lateinit var placeName: TextView
    private lateinit var currentTemp: TextView
    private lateinit var currentSky: TextView
    private lateinit var currentAQI: TextView
    private lateinit var nowLayout: RelativeLayout
    private lateinit var forecastLayout: LinearLayout
    private lateinit var coldRiskText: TextView
    private lateinit var dressingText: TextView
    private lateinit var ultravioletText: TextView
    private lateinit var carWashingText: TextView
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        val controller = decorView.windowInsetsController
//        controller?.hide(WindowInsets.Type.statusBars())
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        weatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        initView()
        initData()
    }

    private fun initData() {
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        viewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }

    private fun showWeatherInfo(weather: Weather) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now.xml布局中的数据
        val currentTempText = "${realtime.temperature.toInt()} °c"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skyCon).info
        val currentPM25Text = "空气指数${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skyCon).bg)
        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val size = daily.skycon.size
        for (i in 0 until size) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this).inflate(
                R.layout.forecast_item,
                forecastLayout, false
            )
            val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
            val skyIcon = view.findViewById<ImageView>(R.id.skyIcon)
            val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
            val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} °c"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml 布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherBinding.scrollView.visibility = View.VISIBLE
    }

    private fun initView() {
        nowBinding = DataBindingUtil.bind(weatherBinding.weatherLayout[0])!!
        forecastBinding = DataBindingUtil.bind(weatherBinding.weatherLayout[1])!!
        lifeIndexBinding = DataBindingUtil.bind(weatherBinding.weatherLayout[2])!!
        placeName = nowBinding.placeName
        currentTemp = nowBinding.currentTemp
        currentSky = nowBinding.currentSky
        currentAQI = nowBinding.currentAQI
        nowLayout = nowBinding.nowLayout
        forecastLayout = forecastBinding.forecastLayout
        coldRiskText = lifeIndexBinding.coldRiskText
        dressingText = lifeIndexBinding.dressingText
        ultravioletText = lifeIndexBinding.ultravioletText
        carWashingText = lifeIndexBinding.carWashingText
    }
}