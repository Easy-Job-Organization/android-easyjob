package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Review
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


data class ReviewRequest(
    val score: Double,
    val comment: String
)

interface ReviewService {
    @POST("reviews/client/{clientId}/professional/{professionalId}")
    suspend fun submitReview(
        @Path("clientId") clientId: String,
        @Path("professionalId") professionalId: String,
        @Body review: ReviewRequest
    ): Response<Review>
}

class ReviewServiceImpl : ReviewService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ReviewService = retrofit.create(ReviewService::class.java)

    override suspend fun submitReview(
        clientId: String,
        professionalId: String,
        review: ReviewRequest
    ): Response<Review> {
        return apiService.submitReview(clientId, professionalId, review)
    }
}