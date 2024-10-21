package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.services.ProfessionalCardResponse
import com.easyjob.jetpack.services.ProfessionalSearchScreenResponse
import com.easyjob.jetpack.services.SearchScreenService
import com.easyjob.jetpack.services.SearchScreenServiceImpl

interface SearchScreenRepository {
    suspend fun fetchProfesionalCards(): List<ProfessionalCardResponse>
    suspend fun fetchProfesionalCardsSearch(city:String, speciality:String): ProfessionalSearchScreenResponse?
}

class SearchScreenRepositoryImpl(
    private val searchScreenService: SearchScreenService = SearchScreenServiceImpl()
) : SearchScreenRepository {
    override suspend fun fetchProfesionalCards(): List<ProfessionalCardResponse> {
        val response = searchScreenService.getProfessionalProfileCards()
        return if (response.isSuccessful) {
            response.body()?.data ?: emptyList()
        }else{
            emptyList()
        }
    }

    override suspend fun fetchProfesionalCardsSearch(city:String, speciality:String): ProfessionalSearchScreenResponse? {
        val response = searchScreenService.getProfessionalByCitySpeciality(city,speciality)
        return response.body()
    }
}
