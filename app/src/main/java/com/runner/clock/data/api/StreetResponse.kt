package com.runner.clock.data.api

import com.google.gson.annotations.SerializedName

data class StreetResponse(
    @SerializedName("result")
    val streets: List<String>
)
