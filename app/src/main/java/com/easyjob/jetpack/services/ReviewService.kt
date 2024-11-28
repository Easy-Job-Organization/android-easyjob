package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.repositories.ReviewClientDTO
import com.easyjob.jetpack.repositories.ReviewProfessionalDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @PATCH("reviews/{reviewId}")
    suspend fun updateReview(
        @Path("reviewId") reviewId: String,
        @Body review: ReviewRequest
    ): Response<Review>

    @GET("reviews/client/{clientId}")
    suspend fun getReviewsByClientId(
        @Path("clientId") clientId: String
    ): Response<List<ReviewClientDTO>>


    @GET("reviews/professional/{professionalId}")
    suspend fun getReviewsByProfessionalId(
        @Path("professionalId") clientId: String
    ): Response<List<ReviewProfessionalDTO>>

}