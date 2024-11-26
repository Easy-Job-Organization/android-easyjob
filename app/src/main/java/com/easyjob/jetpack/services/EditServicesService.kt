package com.easyjob.jetpack.services

import retrofit2.http.GET
import com.easyjob.jetpack.models.Service
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Path

interface EditServicesService {
    @GET("/professionals/services/{id}")
    suspend fun getServicesOfProfessional(@Path("id") id: String): List<Service?>


    @DELETE("/professionals/oneservice/{id}/{serviceId}")
    suspend fun deleteService(
        @Path("id") id: String,
        @Path("serviceId") serviceId : String
    ): Response<Unit>

}
