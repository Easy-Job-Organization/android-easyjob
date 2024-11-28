package com.easyjob.jetpack.services

import com.easyjob.jetpack.repositories.LocationResponseDTO
import com.easyjob.jetpack.repositories.createLocationDTO
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlacesService {

    @GET("/places/professionals/{id}")
    suspend fun getPlacesProfessionals(@Path("id") professionalId: String): List<LocationResponseDTO>

    @POST("/places/professionals/{id}")
    suspend fun savePlaceProfessional(@Path("id") professionalId: String, @Body location: createLocationDTO): Response<LocationResponseDTO>

}

class PlacesServiceImpl : PlacesService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: PlacesService = retrofit.create(PlacesService::class.java)

    override suspend fun getPlacesProfessionals(id: String): List<LocationResponseDTO> {
        val res = apiService.getPlacesProfessionals(id)
        return res
    }

    override suspend fun savePlaceProfessional(
        professionalId: String,
        location: createLocationDTO
    ): Response<LocationResponseDTO> {
        val res = apiService.savePlaceProfessional(professionalId, location)
        return res
    }

}