package com.easyjob.jetpack.repositories

import android.util.Log
import androidx.lifecycle.ViewModel
import com.easyjob.jetpack.services.AuthService
import com.easyjob.jetpack.services.AuthServiceImpl
import com.easyjob.jetpack.services.LoginRequest
import com.easyjob.jetpack.services.LoginResponse
import com.easyjob.jetpack.services.RegisterRequest
import com.easyjob.jetpack.services.RegisterResponse
import retrofit2.Response


interface AuthRepository {
    suspend fun signIn(email: String, password: String): Response<LoginResponse>

    suspend fun signUp(
        name: String,
        last_name: String,
        email: String,
        phone_number: String,
        password: String,
        option: String
    ): Response<RegisterResponse>
}

class AuthRepositoryImpl(
    val authService: AuthService = AuthServiceImpl()
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Response<LoginResponse> {
        val res = authService.loginWithEmailAndPasswordClient(LoginRequest(email, password))
        Log.e(">>>", "The response is: ${res.body()}")
        return res
    }

    override suspend fun signUp(
        name: String,
        last_name: String,
        email: String,
        phone_number: String,
        password: String,
        option: String
    ): Response<RegisterResponse> {
        val res: Response<RegisterResponse>
        if (option == "Cliente") {
            res = authService.registerClient(
                RegisterRequest(
                    name,
                    last_name,
                    email,
                    phone_number,
                    password
                )
            )
        } else {
            res = authService.registerProfessional(
                RegisterRequest(
                    name,
                    last_name,
                    email,
                    phone_number,
                    password
                )
            )
        }

        Log.e(">>>", "The response is: ${res.body()}")
        return res
    }

}