package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @author: YangKui
 * @data: 2024/8/26 16:29
 * @des:
 *
 */
data class PlaceResponse(val status: String, val places: List<Place>)
data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)