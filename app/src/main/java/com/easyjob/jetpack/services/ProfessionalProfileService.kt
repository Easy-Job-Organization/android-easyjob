package com.easyjob.jetpack.services


import com.easyjob.jetpack.models.CitiesResponse
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.models.SpecialitiesResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfessionalProfileService {
    @GET("professionals/{id}")
    suspend fun getProfessionalProfile(@Path("id") id: String): Response<Professional>

    @GET("/professionals/specialities/{id}")
    suspend fun getSpecialities(@Path("id") id: String): Response<List<SpecialitiesResponse>>

    @GET("/professionals/cities/{id}")
    suspend fun getCities(@Path("id") id: String): Response<List<CitiesResponse>>

    @GET("/reviews/professional/{id}")
    suspend fun getReviews(@Path("id") id: String): Response<List<Review>>

}