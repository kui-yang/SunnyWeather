package com.sunnyweather.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sunnyweather.android.databinding.ActivityMainBinding

/**
 * author: YangKui
 * data: 2023/10/25 16:59
 * des:
 */
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}