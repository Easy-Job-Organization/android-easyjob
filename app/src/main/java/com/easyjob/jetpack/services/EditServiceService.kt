package com.easyjob.jetpack.services

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface EditServiceService {

    @PATCH("/services/{id}")
    suspend fun updateService(
        @Path("id") id: String,
        @Body fields: Map<String, Any>
    ): Response<Unit>

    companion object {
        fun create(): EditServiceService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.easyjob.com.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(EditServiceService::class.java)
        }
    }
}