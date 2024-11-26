package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.services.AppointmentService
import com.easyjob.jetpack.services.LikesProfessionalService
import javax.inject.Inject

interface LikesProfessionalRepository {
    suspend fun fetchLikesClient(id: String): List<Professional>
}

class LikesProfessionalRepositoryImpl  @Inject constructor(
    private val likesProfessionalService: LikesProfessionalService
): LikesProfessionalRepository {

    override suspend fun fetchLikesClient(id: String): List<Professional> {
        return likesProfessionalService.getLikesOfClient(id).filterNotNull()
    }

}