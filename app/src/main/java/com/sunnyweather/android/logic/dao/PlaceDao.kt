package com.sunnyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place

/**
 * @author: YangKui
 * @data: 2024/8/29 09:09
 * @des:
 *
 */
object PlaceDao {
    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        return Gson().fromJson(sharedPreferences().getString("place", ""), Place::class.java)
    }
    fun isPlaceSaved() = sharedPreferences().contains("place")
}