package com.example.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Inventory(
    @field:Json(name = "condition") val condition: String,
    @field:Json(name = "quantity") val quantity: Int,
    @field:Json(name = "sellingPrice") val sellingPrice: Double,
    @field:Json(name = "shelfLocation") val shelfLocation: String
)
