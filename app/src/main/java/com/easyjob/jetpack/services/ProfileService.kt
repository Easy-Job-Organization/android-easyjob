package com.easyjob.jetpack.services

import android.util.Log
import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {
    @GET("clients/{id}")
    suspend fun getProfileClient(@Path("id") id: String): Response<Client>

    @GET("professionals/{id}")
    suspend fun getProfileProfessional(@Path("id") id: String): Response<Professional>
}

class ProfileServiceImpl : ProfileService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ProfileService = retrofit.create(ProfileService::class.java)

    override suspend fun getProfileClient(id: String): Response<Client> {
        val response = apiService.getProfileClient(id)
        return response
    }

    override suspend fun getProfileProfessional(id: String): Response<Professional> {
        val response = apiService.getProfileProfessional(id)
        return response
    }

}