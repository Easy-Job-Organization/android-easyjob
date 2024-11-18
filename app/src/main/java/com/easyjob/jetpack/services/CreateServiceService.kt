package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Service
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Path

interface CreateServiceService {

    @GET("/professionals/service/{id}/{serviceId}")
    suspend fun createService(
        @Path("id") id: String,
        @Path("serviceId") serviceId : String
    ): Response<Unit>


    @GET("/services/")
    suspend fun getAllServices(): List<Service>

}
