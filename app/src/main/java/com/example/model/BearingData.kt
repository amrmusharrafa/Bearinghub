package com.example.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BearingData(
    @field:Json(name = "bearing") val bearing: Bearing,
    @field:Json(name = "inventory") val inventory: List<Inventory> = emptyList()
)
