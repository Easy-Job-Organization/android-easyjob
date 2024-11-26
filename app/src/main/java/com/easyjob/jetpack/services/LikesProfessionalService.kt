package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Professional
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface LikesProfessionalService {

    @GET("/likes/client/{id}")
    suspend fun getLikesOfClient(@Path("id") id: String): List<Professional?>

}

class LikesProfessionalServiceImpl : LikesProfessionalService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: LikesProfessionalService = retrofit.create(LikesProfessionalService::class.java)

    override suspend fun getLikesOfClient(id: String): List<Professional?> {
        val res = apiService.getLikesOfClient(id)
        return res
    }

}