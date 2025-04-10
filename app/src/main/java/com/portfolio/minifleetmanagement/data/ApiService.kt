package com.portfolio.minifleetmanagement.data

import com.portfolio.minifleetmanagement.data.response.IntResponse
import com.portfolio.minifleetmanagement.data.response.StringResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("fleet/speed")
    fun getSpeed(): Call<IntResponse>

    @GET("fleet/door")
    fun getDoorStatus(): Call<StringResponse>

    @GET("fleet/engine")
    fun getEngineStatus(): Call<StringResponse>
}