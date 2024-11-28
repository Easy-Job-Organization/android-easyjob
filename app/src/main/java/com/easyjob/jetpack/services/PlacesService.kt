package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.repositories.LocationResponseDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PlacesService {

    @GET("/places/professionals/{id}")
    suspend fun getPlacesProfessionals(@Path("id") professionalId: String): List<LocationResponseDTO>

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

}