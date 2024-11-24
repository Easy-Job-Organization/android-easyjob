package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


data class ReviewRequest(
    val score: Double,
    val comment: String
)

interface ReviewService {
    @POST("reviews/client/{clientId}/profesional/{professionalId}")
    suspend fun submitReview(
        @Path("clientId") clientId: String,
        @Path("professionalId") professionalId: String,
        @Body review: ReviewRequest
    ): Response<Review>
}