package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.services.ProfessionalCardResponse
import com.easyjob.jetpack.services.ProfessionalCardResponseWithoutCity
import com.easyjob.jetpack.services.ProfessionalSearchScreenResponse
import com.easyjob.jetpack.services.SearchScreenService
import com.easyjob.jetpack.services.SearchScreenServiceImpl
import javax.inject.Inject

interface SearchScreenRepository {
    suspend fun fetchProfesionalCards(): List<ProfessionalCardResponse>
    suspend fun fetchProfesionalCardsSearch(city:String, speciality:String): ProfessionalSearchScreenResponse?
    suspend fun fetchProfesionalSearch(speciality: String): List<ProfessionalCardResponseWithoutCity>?
}


class SearchScreenRepositoryImpl @Inject constructor(
    private val searchScreenService: SearchScreenService
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

    override suspend fun fetchProfesionalSearch(speciality: String): List<ProfessionalCardResponseWithoutCity>? {
        val response = searchScreenService.getProfessionalSpeciality(speciality)
        return response.body()
    }

}
