package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.services.ReviewRequest
import com.easyjob.jetpack.services.ReviewService
import retrofit2.Response
import javax.inject.Inject

interface ReviewRepository {
    suspend fun submitReview(clientId: String, professionalId: String, score: Double, comment: String): Response<Review>
}

class ReviewRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService
) : ReviewRepository {
    override suspend fun submitReview(clientId: String, professionalId: String, score: Double, comment: String): Response<Review> {
        val reviewRequest = ReviewRequest(score, comment)
        return reviewService.submitReview(clientId, professionalId, reviewRequest)
    }
}