package com.easyjob.jetpack.services

import retrofit2.http.GET
import com.easyjob.jetpack.models.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

interface EditServicesService {
    @GET("/professionals/services/{id}")
    suspend fun getServicesOfProfessional(@Path("id") id: String): List<Service?>
}
