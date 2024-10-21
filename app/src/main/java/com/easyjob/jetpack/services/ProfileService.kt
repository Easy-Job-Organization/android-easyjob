package com.easyjob.jetpack.services

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {
    @GET("clients/{id}")
    suspend fun getProfile(@Path("id") id: String): Response<ProfileResponse>

}


data class ProfileResponse(
    val id: String,
    val name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val photo_url: String,
    val roles: List<String>
)

class ProfileServiceImpl : ProfileService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ProfileService = retrofit.create(ProfileService::class.java)

    override suspend fun getProfile(id: String): Response<ProfileResponse> {
        return apiService.getProfile(id)
    }

}