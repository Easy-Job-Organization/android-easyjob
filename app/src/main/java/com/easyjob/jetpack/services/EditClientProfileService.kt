package com.easyjob.jetpack.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import com.easyjob.jetpack.models.Client
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

interface EditClientProfileService {

    @Multipart
    @PATCH("clients/{id}")
    suspend fun editClient(
        @Path("id") id: String,
        @Part("name") name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part client_image: MultipartBody.Part?,

        ): Response<Unit>

    @GET("clients/{id}")
    suspend fun getCurrentClient(
        @Path("id") id : String
    ): Response<Client>
}