package com.easyjob.jetpack.services

import retrofit2.http.POST
import retrofit2.http.Path

interface RecoverPassService {
    @POST("auth/user/reset-password/{email}")
    suspend fun recoverPass(
        @Path("email") email: String,
    )
}