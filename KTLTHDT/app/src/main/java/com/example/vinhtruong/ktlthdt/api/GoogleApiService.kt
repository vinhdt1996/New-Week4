package com.example.vinhtruong.ktlthdt.api


import com.example.vinhtruong.ktlthdt.model.ResultAddress
import com.example.vinhtruong.ktlthdt.model.ResultRoute
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GoogleApiService {

    @GET("/maps/api/directions/json")
    fun getRoute(@Query("origin") origin: String,
                 @Query("destination") destination: String)
            : Call<ResultRoute>

    @GET("maps/api/geocode/json")
    fun getAddress(@Query("latlng") latlng: String)
            : Call<ResultAddress>

    companion object {
        const val BASE_URL: String = "https://maps.googleapis.com"
    }
}