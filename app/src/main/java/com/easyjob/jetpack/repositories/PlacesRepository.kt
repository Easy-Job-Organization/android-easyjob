package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.services.PlacesService
import com.easyjob.jetpack.services.ReviewRequest
import com.easyjob.jetpack.services.ReviewService
import retrofit2.Response
import javax.inject.Inject

data class LocationResponseDTO (
    val id: String,
    val name: String,
    val longitude: Double,
    val latitude: Double
)


interface PlacesRepository {
    suspend fun getLocationsByProfessional(professionalId: String): List<LocationResponseDTO>
}

class PlacesRepositoryImpl @Inject constructor(
    private val placesService: PlacesService
) : PlacesRepository {
    override suspend fun getLocationsByProfessional(professionalId: String): List<LocationResponseDTO> {
        return placesService.getPlacesProfessionals(professionalId)
    }

}