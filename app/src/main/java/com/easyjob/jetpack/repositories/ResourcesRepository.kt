package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.services.CitiesResponse
import com.easyjob.jetpack.services.LanguagesResponse
import com.easyjob.jetpack.services.ResourcesService
import com.easyjob.jetpack.services.ResourcesServiceImpl
import com.easyjob.jetpack.services.ServicesResponse
import com.easyjob.jetpack.services.SpecialityResponse
import retrofit2.Response

interface ResourcesRepository {
    suspend fun getCities(): Response<CitiesResponse>
    suspend fun getLanguages(): Response<LanguagesResponse>
    suspend fun getServices(): Response<ServicesResponse>
    suspend fun getSpecialities(): Response<SpecialityResponse>
}

class ResourcesRepositoryImpl(
    val resourcesService: ResourcesService = ResourcesServiceImpl()
) : ResourcesRepository {
    override suspend fun getCities(): Response<CitiesResponse> {
        return resourcesService.getCities()
    }

    override suspend fun getLanguages(): Response<LanguagesResponse> {
        return resourcesService.getLanguages()
    }

    override suspend fun getServices(): Response<ServicesResponse> {
        return resourcesService.getServices()
    }

    override suspend fun getSpecialities(): Response<SpecialityResponse> {
        return resourcesService.getSpecialities()
    }
}