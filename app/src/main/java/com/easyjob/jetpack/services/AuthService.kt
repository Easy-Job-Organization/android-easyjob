package com.easyjob.jetpack.services

import android.util.Log
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Login
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String)

// Register
data class RegisterRequest(
    val name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val password: String
)
data class RegisterResponse(val token: String)

// Define the AuthService interface
interface AuthService {
    @POST("auth/client/login")
    suspend fun loginWithEmailAndPasswordClient(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/client/register")
    suspend fun registerClient(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("auth/professional/register")
    suspend fun registerProfessional(@Body request: RegisterRequest): Response<RegisterResponse>

}

class AuthServiceImpl : AuthService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/") // Replace with your actual base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: AuthService = retrofit.create(AuthService::class.java)

    override suspend fun loginWithEmailAndPasswordClient(request: LoginRequest): Response<LoginResponse> {
        val res = apiService.loginWithEmailAndPasswordClient(request)
        return res
    }

    override suspend fun registerClient(request: RegisterRequest): Response<RegisterResponse> {
        val res = apiService.registerClient(request)
        return res
    }

    override suspend fun registerProfessional(request: RegisterRequest): Response<RegisterResponse> {
        val res = apiService.registerProfessional(request)
        return res
    }
}