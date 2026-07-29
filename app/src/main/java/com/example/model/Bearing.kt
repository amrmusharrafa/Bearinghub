package com.example.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Bearing(
    @field:Json(name = "number") val number: String,
    @field:Json(name = "manufacturer") val manufacturer: String,
    @field:Json(name = "boreMm") val boreMm: Double,
    @field:Json(name = "outsideMm") val outsideMm: Double,
    @field:Json(name = "widthMm") val widthMm: Double,
    @field:Json(name = "chamferMm") val chamferMm: Double,
    @field:Json(name = "weightKg") val weightKg: Double
)
