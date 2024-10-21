package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.services.CitiesResponse
import com.easyjob.jetpack.services.ProfessionalProfileResponse
import com.easyjob.jetpack.services.ProfessionalProfileService
import com.easyjob.jetpack.services.ProfessionalProfileServiceImpl
import com.easyjob.jetpack.services.SpecialitiesResponse
import retrofit2.Response

interface ProfessionalProfileRepository {
    suspend fun fetchProfessionalProfile(id: String): Response<ProfessionalProfileResponse>

    suspend fun fetchCities(id: String): CitiesResponse?

    suspend fun fetchReviews(id: String): Int

    suspend fun fetchSpecialities(id: String): List<SpecialitiesResponse>
}

class ProfessionalProfileRepositoryImpl(
    private val professionalProfileService: ProfessionalProfileService = ProfessionalProfileServiceImpl()
) : ProfessionalProfileRepository {
    override suspend fun fetchProfessionalProfile(id: String): Response<ProfessionalProfileResponse> {
        return professionalProfileService.getProfessionalProfile(id)
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