package com.example.diseasemonitoring

import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("/api/diseases")
    fun addDisease(@Body disease:Disease):call<Disease>
}