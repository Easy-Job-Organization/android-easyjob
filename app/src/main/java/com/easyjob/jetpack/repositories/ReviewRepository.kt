package com.easyjob.jetpack.repositories

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

interface ReviewRepository {
    suspend fun submitReview(clientId: String, professionalId: String, score: Double, comment: String): Response<Review>
    suspend fun getReviewsByClientId(clientId: String): Response<List<ReviewClientDTO>>
}

class ReviewRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService
) : ReviewRepository {
    override suspend fun submitReview(clientId: String, professionalId: String, score: Double, comment: String): Response<Review> {
        val reviewRequest = ReviewRequest(score, comment)
        return reviewService.submitReview(clientId, professionalId, reviewRequest)
    }

    override suspend fun getReviewsByClientId(clientId: String): Response<List<ReviewClientDTO>> {
        return reviewService.getReviewsByClientId(clientId)
    }
}