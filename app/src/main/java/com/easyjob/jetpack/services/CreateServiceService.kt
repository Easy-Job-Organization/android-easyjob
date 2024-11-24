package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.CreateServiceDTO
import com.easyjob.jetpack.models.Service
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CreateServiceService {

    @POST("/services/")
    suspend fun createService(
        @Body selectedService : CreateServiceDTO
    ): Response<Service>

    @GET("professionals/service/{id}/{serviceId}")
    suspend fun linkServiceProfessional(
        @Path("id") id: String,
        @Path("serviceId") serviceId : String
    ): Response<Unit>

    @PATCH("/services/{id}")
    suspend fun updateService(
        @Path("id") id: String,
        @Body updates: @JvmSuppressWildcards Map<String, Any>
    ): Response<Service>


    @GET("/services/")
    suspend fun getAllServices(): List<Service>

}
