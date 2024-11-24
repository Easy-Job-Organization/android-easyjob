package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.CitiesResponse
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.SpecialitiesResponse
import com.easyjob.jetpack.services.ProfessionalProfileService
import retrofit2.Response
import javax.inject.Inject

interface ProfessionalProfileRepository {
    suspend fun fetchProfessionalProfile(id: String): Response<Professional>

    suspend fun fetchCities(id: String): CitiesResponse?

    suspend fun fetchReviews(id: String): Int

    suspend fun fetchSpecialities(id: String): List<SpecialitiesResponse>

    suspend fun likeProfessional(clientId: String, professionalId: String)
}

class ProfessionalProfileRepositoryImpl @Inject constructor(
    private val professionalProfileService: ProfessionalProfileService
) : ProfessionalProfileRepository {
    override suspend fun fetchProfessionalProfile(id: String): Response<Professional> {
        return professionalProfileService.getProfessionalProfile(id)
    }

    override suspend fun likeProfessional(clientId: String, professionalId: String) {
        professionalProfileService.likeProfessional(clientId, professionalId)
    }

    override suspend fun fetchCities(id: String): CitiesResponse? {
        val response = professionalProfileService.getCities(id)
        return if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            response.body()?.firstOrNull()
        } else {
            null
        }
    }

    override suspend fun fetchReviews(id: String): Int {
        val response = professionalProfileService.getReviews(id)
        return if (response.isSuccessful) {
            response.body()?.size ?: 0
        } else {
            0
        }
    }

    override suspend fun fetchSpecialities(id: String): List<SpecialitiesResponse> {
        val response = professionalProfileService.getSpecialities(id)
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}