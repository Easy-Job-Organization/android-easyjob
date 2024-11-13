package com.easyjob.jetpack.services

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import android.util.Log

interface SearchScreenService {
    @GET("professionals")
    suspend fun getProfessionalProfileCards(): Response<ProfessionalSearchScreenResponse>

    @GET("professionals/city/{city}/speciality/{speciality}")
    suspend fun getProfessionalByCitySpeciality(@Path("city") city:String, @Path("speciality") speciality:String): Response<ProfessionalSearchScreenResponse>
}

data class ProfessionalSearchScreenResponse(
    val data: List<ProfessionalCardResponse>,
    val total: Int
)

data class ProfessionalCardResponse(
    val id: String,
    val name: String,
    val last_name: String,
    val email: String,
    val phone_number: String,
    val photo_url: String,
    val roles: List<String>,
    val description: String?,
    val score : Double?
)

class SearchScreenServiceImpl : SearchScreenService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: SearchScreenService = retrofit.create(SearchScreenService::class.java)

    override suspend fun getProfessionalProfileCards(): Response<ProfessionalSearchScreenResponse> {
        return apiService.getProfessionalProfileCards()
    }

    override suspend fun getProfessionalByCitySpeciality(
        city: String,
        speciality: String
    ): Response<ProfessionalSearchScreenResponse> {
        return  apiService.getProfessionalByCitySpeciality(city, speciality)
    }

}