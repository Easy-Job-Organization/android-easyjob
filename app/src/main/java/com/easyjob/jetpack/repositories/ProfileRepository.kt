package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.services.ProfileResponse
import com.easyjob.jetpack.services.ProfileService
import com.easyjob.jetpack.services.ProfileServiceImpl
import retrofit2.Response

interface ProfileRepository {
    suspend fun fetchProfile(id: String): Response<ProfileResponse>
}

class ProfileRepositoryImpl(
    private val profileService: ProfileService = ProfileServiceImpl()
) : ProfileRepository {
    override suspend fun fetchProfile(id: String): Response<ProfileResponse> {
        return profileService.getProfile(id)
    }
}