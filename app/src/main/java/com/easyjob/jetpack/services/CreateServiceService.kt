package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.viewmodels.CreateServiceDTO
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CreateServiceService {

    @GET("/professionals/service/{id}/{serviceId}")
    suspend fun linkProfessionalToService(
        @Path("id") id: String,
        @Path("serviceId") serviceId : String
    ): Response<Unit>

    @POST("/services")
    suspend fun createService(
        @Body createServiceDTO: CreateServiceDTO
    ): Response<Service>

    @PATCH("/services/{id}")
    suspend fun updateService(
        @Path("id") id: String,
        @Body updates: @JvmSuppressWildcards Map<String, Any>
    ): Response<Service>


    @GET("/services/")
    suspend fun getAllServices(): List<Service>

}
