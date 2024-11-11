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

class EditServicesServiceImpl : EditServicesService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: EditServicesService = retrofit.create(EditServicesService::class.java)

    override suspend fun getServicesOfProfessional(id: String): List<Service?> {
        val res = apiService.getServicesOfProfessional(id)
        return res
    }
}