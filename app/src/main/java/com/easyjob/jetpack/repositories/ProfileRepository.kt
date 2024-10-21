package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.services.ProfileService
import com.easyjob.jetpack.services.ProfileServiceImpl
import retrofit2.Response

interface ProfileRepository {
    suspend fun fetchProfile(id: String): Response<Professional>
}

class ProfileRepositoryImpl(
    private val profileService: ProfileService = ProfileServiceImpl()
) : ProfileRepository {
    override suspend fun fetchProfile(id: String): Response<Professional> {
        return profileService.getProfile(id)
    }
}