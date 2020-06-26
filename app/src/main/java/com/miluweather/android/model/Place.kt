package com.miluweather.android.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: QuYunShuo
 * @Time: 2020/6/26
 * @Class: Place
 * @Remark:
 */
data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)