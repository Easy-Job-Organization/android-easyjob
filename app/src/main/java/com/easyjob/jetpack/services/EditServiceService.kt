package com.easyjob.jetpack.services

import android.util.Log
import com.easyjob.jetpack.models.Service
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EditServiceService {

    @PATCH("/services/{id}")
    suspend fun updateService(
        @Path("id") id: String,
        @Body fields: Map<String, Any>
    ): Response<Unit>

    @GET("/services/{id}")
    suspend fun getService(
        @Path("id") id: String
    ): Response<Service>
}

class EditServiceServiceImpl : EditServiceService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: EditServiceService = retrofit.create(EditServiceService::class.java)
    override suspend fun updateService(id: String, fields: Map<String, Any>): Response<Unit> {
        return  apiService.updateService(id, fields)
    }

    override suspend fun getService(id: String): Response<Service> {
        Log.d("EditServiceService", "<<<<<<<<>>>>>>>> Entra al service - ${id}")
        val res = apiService.getService(id)
        Log.d("EditServiceService", "<<<<<<<<>>>>>>>>  response - ${res}")
        return res
    }
}