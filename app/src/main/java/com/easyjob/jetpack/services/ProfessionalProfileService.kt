package com.easyjob.jetpack.services


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfessionalProfileService {
    @GET("professionals/{id}")
    suspend fun getProfessionalProfile(@Path("id") id: String): Response<ProfessionalProfileResponse>

    @GET("/professionals/specialities/{id}")
    suspend fun getSpecialities(@Path("id") id: String): Response<List<SpecialitiesResponse>>

    @GET("/professionals/cities/{id}")
    suspend fun getCities(@Path("id") id: String): Response<List<CitiesResponse>>

    @GET("/reviews/professional/{id}")
    suspend fun getReviews(@Path("id") id: String): Response<List<ReviewsResponse>>

}

data class ReviewsResponse(
    val id: String,
    val score: Double,
    val comment: String,
    val client: ProfileResponse
)

data class SpecialitiesResponse(
    val id: String,
    val name: String
)

data class CitiesResponse(
    val id: String,
    val name: String
)

data class ProfessionalProfileResponse(
    val id: String,
    val name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val photo_url: String,
    val roles: List<String>,
    val description: String,
    val score : Double
)

class ProfessionalProfileServiceImpl : ProfessionalProfileService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ProfessionalProfileService = retrofit.create(ProfessionalProfileService::class.java)

    override suspend fun getProfessionalProfile(id: String): Response<ProfessionalProfileResponse> {
        return apiService.getProfessionalProfile(id)
    }

    override suspend fun getSpecialities(id: String): Response<List<SpecialitiesResponse>> {
        return apiService.getSpecialities(id)
    }

    override suspend fun getCities(id: String): Response<List<CitiesResponse>> {
        return apiService.getCities(id)
    }

    override suspend fun getReviews(id: String): Response<List<ReviewsResponse>> {
        return apiService.getReviews(id)
    }

}