package com.example.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @field:Json(name = "success") val success: Boolean,
    @field:Json(name = "data") val data: BearingData? = null,
    @field:Json(name = "message") val message: String? = null
)
