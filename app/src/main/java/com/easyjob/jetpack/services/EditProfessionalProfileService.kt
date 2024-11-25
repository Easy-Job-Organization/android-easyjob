package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.SpecialitiesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import com.easyjob.jetpack.models.City
import com.easyjob.jetpack.models.Professional
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

interface EditProfessionalProfileService {

    @Multipart
    @PATCH("professional/{id}")
    suspend fun editProfessional(
        @Path("id") id : String,
        @Part("name") name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("phone_number") phone_number: RequestBody,
        @Part professional_image: MultipartBody.Part,
        @Part("city_id") city: RequestBody,
        @Part("speciality_id") speciality: RequestBody,

        ): Response<Unit>

    @GET("professionals/{id}")
    suspend fun getCurrentProfessional(
        @Path("id") id : String
    ): Response<Professional>

    @GET("/specialities/")
    suspend fun getSpecialities(): Response<List<SpecialitiesResponse>>

    @GET("/city/")
    suspend fun getCities(): Response<List<City>>
}