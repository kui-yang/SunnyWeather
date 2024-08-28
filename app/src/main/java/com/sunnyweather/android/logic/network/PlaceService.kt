package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: YangKui
 * @data: 2024/8/26 16:37
 * @des:
 *
 */
interface PlaceService {
    @GET("v2/place?&token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String):Call<PlaceResponse>
}