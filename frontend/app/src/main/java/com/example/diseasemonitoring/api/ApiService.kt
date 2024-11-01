package com.example.diseasemonitoring.api

import com.example.diseasemonitoring.models.Disease
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    @GET("api/diseases/name/{name}")
    fun getDiseaseByName(@Path("name") name: String): Call<Disease>
    @POST("/api/diseases")
    fun addDisease(@Body disease: Disease):Call<Disease>
}