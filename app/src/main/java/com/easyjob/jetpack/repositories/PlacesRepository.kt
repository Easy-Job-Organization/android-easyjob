package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.services.PlacesService
import retrofit2.Response
import javax.inject.Inject

data class LocationResponseDTO (
    val id: String,
    val name: String,
    val longitude: Double,
    val latitude: Double
)

data class createLocationDTO (
    val name: String,
    val longitude: Double,
    val latitude: Double
)


interface PlacesRepository {
    suspend fun getLocationsByProfessional(professionalId: String): List<LocationResponseDTO>
    suspend fun saveLocationByProfessional(professionalId: String, name: String, longitude: Double, latitude: Double): Response<LocationResponseDTO>
}

class PlacesRepositoryImpl @Inject constructor(
    private val placesService: PlacesService
) : PlacesRepository {
    override suspend fun getLocationsByProfessional(professionalId: String): List<LocationResponseDTO> {
        return placesService.getPlacesProfessionals(professionalId)
    }

    override suspend fun saveLocationByProfessional(professionalId: String, name: String, longitude: Double, latitude: Double): Response<LocationResponseDTO> {
        return placesService.savePlaceProfessional(
            professionalId,
            createLocationDTO(name, longitude, latitude)
        )
    }

}