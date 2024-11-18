package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Service
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EditServiceService {

    @PATCH("/services/{id}")
    suspend fun updateService(
        @Path("id") id: String,
        @Body updates: @JvmSuppressWildcards Map<String, Any>
    ): Response<Service>


    @GET("/services/{id}")
    suspend fun getService(
        @Path("id") id: String
    ): Response<Service>
}