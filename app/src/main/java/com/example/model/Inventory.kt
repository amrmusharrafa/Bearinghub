package com.example.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Inventory(
    @field:Json(name = "condition") val condition: String = "New",
    @field:Json(name = "quantity") val quantity: Int? = null,
    @field:Json(name = "sellingPrice") val sellingPrice: Double? = null,
    @field:Json(name = "shelfLocation") val shelfLocation: String = "",
    @field:Json(name = "currency") val currency: String = "EGP"
)

