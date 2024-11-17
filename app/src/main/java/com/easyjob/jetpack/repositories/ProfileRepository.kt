package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.services.ProfileService
import com.easyjob.jetpack.services.ProfileServiceImpl
import retrofit2.Response
import javax.inject.Inject

interface ProfileRepository {
    suspend fun fetchProfileProfessional(id: String): Response<Professional>
    suspend fun fetchProfileClient(id: String): Response<Client>
}

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService
) : ProfileRepository {

    override suspend fun fetchProfileProfessional(id: String): Response<Professional> {
        return profileService.getProfileProfessional(id)
    }

    override suspend fun fetchProfileClient(id: String): Response<Client> {
        return profileService.getProfileClient(id)
    }
}