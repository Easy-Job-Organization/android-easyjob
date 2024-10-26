package com.easyjob.jetpack.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

// Login
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val id: String,
    val name: String,
    val lastName: String,
    val password: String,
    val roles: List<String>,
    val token: String,
)

data class RegisterResponse(val token: String)

interface AuthService {
    @POST("auth/user/login")
    suspend fun loginWithEmailAndPasswordClient(@Body request: LoginRequest): Response<LoginResponse>

    @Multipart
    @POST("auth/client/register")
    suspend fun registerClient(
        @Part("name") name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("password") password: RequestBody,
        @Part client_image: MultipartBody.Part
    ): Response<RegisterResponse>

    @Multipart
    @POST("auth/professional/register")
    suspend fun registerProfessional(
        @Part("name") name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part("password") password: RequestBody,
        @Part professional_image: MultipartBody.Part,
        @Part("service_id") service: RequestBody,
        @Part("language_id") language: RequestBody,
        @Part("city_id") city: RequestBody,
        @Part("speciality_id") speciality: RequestBody,

        ): Response<RegisterResponse>

}

class AuthServiceImpl : AuthService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: AuthService = retrofit.create(AuthService::class.java)

    override suspend fun loginWithEmailAndPasswordClient(request: LoginRequest): Response<LoginResponse> {
        val res = apiService.loginWithEmailAndPasswordClient(request)
        return res
    }

    override suspend fun registerClient(
        name: RequestBody,
        last_name: RequestBody,
        email: RequestBody,
        phone_number: RequestBody,
        password: RequestBody,
        client_image: MultipartBody.Part
    ): Response<RegisterResponse> {
        val res = apiService.registerClient(
            name,
            last_name,
            email,
            phone_number,
            password,
            client_image
        )
        return res
    }

    override suspend fun registerProfessional(
        name: RequestBody,
        last_name: RequestBody,
        email: RequestBody,
        phone_number: RequestBody,
        password: RequestBody,
        professional_image: MultipartBody.Part,
        service: RequestBody,
        language: RequestBody,
        city: RequestBody,
        speciality: RequestBody,
    ): Response<RegisterResponse> {
        val res = apiService.registerProfessional(
            name,
            last_name,
            email,
            phone_number,
            password,
            professional_image,
            service,
            language,
            city,
            speciality
        )
        return res
    }

}