package com.sunnyweather.android.logic

import androidx.lifecycle.liveData
import com.sunnyweather.android.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers

/**
 * @author: YangKui
 * @data: 2024/8/26 17:18
 * @des:
 *
 */
object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}