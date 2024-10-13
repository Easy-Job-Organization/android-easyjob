package com.easyjob.jetpack.repositories

import android.util.Log
import androidx.lifecycle.ViewModel
import com.easyjob.jetpack.services.AuthService
import com.easyjob.jetpack.services.AuthServiceImpl
import com.easyjob.jetpack.services.LoginRequest
import com.easyjob.jetpack.services.LoginResponse
import retrofit2.Response


interface AuthRepository {
    suspend fun signIn(email: String, password: String): Response<LoginResponse>
}

class AuthRepositoryImpl(
    val authService: AuthService = AuthServiceImpl()
): AuthRepository {
    override suspend fun signIn(email: String, password: String): Response<LoginResponse> {
        val res = authService.loginWithEmailAndPasswordClient(LoginRequest(email, password))
        Log.e(">>>", "The response is: ${res.body()}")
        return res
    }

}