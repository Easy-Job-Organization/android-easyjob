package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {
    @GET("clients/{id}")
    suspend fun getProfile(@Path("id") id: String): Response<Professional>
}

class ProfileServiceImpl : ProfileService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ProfileService = retrofit.create(ProfileService::class.java)

    override suspend fun getProfile(id: String): Response<Professional> {
        return apiService.getProfile(id)
    }

}