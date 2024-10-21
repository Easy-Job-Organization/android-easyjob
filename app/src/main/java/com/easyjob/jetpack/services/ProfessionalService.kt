package com.easyjob.jetpack.network

import retrofit2.http.GET
import retrofit2.Call
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.AuthService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

interface ProfessionalService {
    @GET("/professionals/{id}")
    suspend fun getProfessional(@Path("id") id: String): Professional?

    @GET("/professionals/services/{id}")
    suspend fun getServicesOfProfessional(@Path("id") id: String): List<Service?>

    @GET("/reviews/professional/{id}")
    suspend fun getReviewsOfProfessional(@Path("id") id: String): List<Review?>
}

class ProfessionalServiceImpl: ProfessionalService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/") // Replace with your actual base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ProfessionalService = retrofit.create(ProfessionalService::class.java)

    override suspend fun getProfessional(id: String): Professional? {
        val res = apiService.getProfessional(id)
        //val professionalObject = res.toO
        return res
    }

    override suspend fun getServicesOfProfessional(id: String): List<Service?> {
        val res = apiService.getServicesOfProfessional(id)
        return res
    }

    override suspend fun getReviewsOfProfessional(id: String): List<Review?> {
        val res = apiService.getReviewsOfProfessional(id)
        return res
    }

}