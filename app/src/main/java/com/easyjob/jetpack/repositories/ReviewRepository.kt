package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.services.ReviewRequest
import com.easyjob.jetpack.services.ReviewService
import retrofit2.Response
import javax.inject.Inject

data class ReviewClientDTO(
    val id: String,
    val score: Double,
    val comment: String,
    val professional: Professional
)

data class ReviewProfessionalDTO(
    val id: String,
    val score: Double,
    val comment: String,
    val client: Client
)

interface ReviewRepository {
    suspend fun submitReview(
        clientId: String,
        professionalId: String,
        score: Double,
        comment: String
    ): Response<Review>

    suspend fun updateReview(reviewId: String, score: Double, comment: String): Response<Review>
    suspend fun getReviewsByClientId(clientId: String): Response<List<ReviewClientDTO>>
    suspend fun getReviewsByProfessionalId(professionalId: String): Response<List<ReviewProfessionalDTO>>
}

class ReviewRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService
) : ReviewRepository {
    override suspend fun submitReview(
        clientId: String,
        professionalId: String,
        score: Double,
        comment: String
    ): Response<Review> {
        val reviewRequest = ReviewRequest(score, comment)
        val response = reviewService.submitReview(clientId, professionalId, reviewRequest)
        if (!response.isSuccessful) {
            Log.e("ReviewRepository", "Error al enviar reseña: ${response.errorBody()?.string()}")
        }
        return response
    }

    override suspend fun updateReview(
        reviewId: String,
        score: Double,
        comment: String
    ): Response<Review> {
        val reviewRequest = ReviewRequest(score, comment)
        val response = reviewService.updateReview(reviewId, reviewRequest)
        if (!response.isSuccessful) {
            Log.e(
                "ReviewRepository",
                "Error al actualizar reseña: ${response.errorBody()?.string()}"
            )
        }
        return response
    }

    override suspend fun getReviewsByClientId(clientId: String): Response<List<ReviewClientDTO>> {
        return reviewService.getReviewsByClientId(clientId)
    }

    override suspend fun getReviewsByProfessionalId(professionalId: String): Response<List<ReviewProfessionalDTO>> {
        return reviewService.getReviewsByProfessionalId(professionalId)
    }
}