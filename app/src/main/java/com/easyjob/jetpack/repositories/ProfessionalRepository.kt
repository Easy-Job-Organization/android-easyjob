package com.easyjob.jetpack.repository

import android.util.Log
import com.easyjob.jetpack.network.ProfessionalService
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.network.ProfessionalServiceImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response

interface ProfessionalRepository {
    suspend fun getProfessional(id: String): Professional?
    suspend fun getServicesOfProfessional(id: String): List<Service?>
    suspend fun getReviewOfProfessional(id: String): List<Review?>
}

class ProfessionalRepositoryImpl(
    val professionalService: ProfessionalService = ProfessionalServiceImpl()
): ProfessionalRepository {
    override suspend fun getProfessional(id: String): Professional? {
        val res = professionalService.getProfessional(id)
        Log.e(">>>", "The response is: ${res}")
        if (res != null) {
            return res
        } else {
            return res
        }
    }

    override suspend fun getServicesOfProfessional(id: String): List<Service?> {
        val res = professionalService.getServicesOfProfessional(id)
        Log.e(">>>", "The response is: ${res}")
        if (res != null) {
            return res
        } else {
            return res
        }
    }

    override suspend fun getReviewOfProfessional(id: String): List<Review?> {
        val res = professionalService.getReviewsOfProfessional(id)
        Log.e(">>>", "The response is: ${res}")
        if (res != null) {
            return res
        } else {
            return res
        }
    }

}
