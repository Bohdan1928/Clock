package com.runner.clock.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/3/action/datastore_search")
    fun getStreets(@Query("resource_id") resourceId: String): Call<String>
}