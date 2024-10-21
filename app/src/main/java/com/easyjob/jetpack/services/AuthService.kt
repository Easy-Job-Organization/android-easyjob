package com.easyjob.jetpack.services

import android.util.Log
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

// Define the AuthService interface
interface AuthService {
    @POST("auth/client/login")
    suspend fun loginWithEmailAndPasswordClient(@Body request: LoginRequest): Response<LoginResponse>
}

class AuthServiceImpl: AuthService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/") // Replace with your actual base URL emilysmith@example.com
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: AuthService = retrofit.create(AuthService::class.java)

    override suspend fun loginWithEmailAndPasswordClient(request: LoginRequest): Response<LoginResponse> {
        val res = apiService.loginWithEmailAndPasswordClient(request)
        return res
    }
}