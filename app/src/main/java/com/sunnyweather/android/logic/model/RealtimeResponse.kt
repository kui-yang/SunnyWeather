package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @author: YangKui
 * @data: 2024/8/28 10:29
 * @des:
 *
 */
data class RealtimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: Realtime)

    data class Realtime(
        @SerializedName("skycon") val skyCon: String,
        val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)
    data class AQI(val chn: Float)
}