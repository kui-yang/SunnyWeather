package com.sunnyweather.android.logic.model

/**
 * @author: YangKui
 * @data: 2024/8/28 10:49
 * @des:
 *
 */
data class Weather(val realtime: RealtimeResponse.Realtime,val daily:DailyResponse.Daily)