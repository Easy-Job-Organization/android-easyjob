package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repo: ReviewRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {


    sealed class ReviewState {
        object Idle : ReviewState()
        object Loading : ReviewState()
        object Success : ReviewState()
        data class Error(val message: String) : ReviewState()
    }

    private val _state = MutableStateFlow<ReviewState>(ReviewState.Idle)
    val state: StateFlow<ReviewState> = _state

    private val _oldReview = MutableStateFlow<Triple<Double, String, String>?>(null)
    val oldReview: StateFlow<Triple<Double, String, String>?> = _oldReview

    fun submitReview(
        professional: String,
        score: Double,
        comment: String,
        reviewId: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val clientId = userPreferencesRepository.userIdFlow.firstOrNull() ?: return@launch
            _state.value = ReviewState.Loading

            val response = if (reviewId == null) {
                repo.submitReview(clientId, professional, score, comment)
            } else {
                repo.updateReview(reviewId, score, comment)
            }

            _state.value = if (response.isSuccessful) {
                ReviewState.Success
            } else {
                ReviewState.Error("Error al enviar la reseña. Inténtalo nuevamente.")
            }
        }
    }

    fun getOldReview(reviews: List<Review?>?) {
        viewModelScope.launch(Dispatchers.IO) {
            val clientId = userPreferencesRepository.userIdFlow.firstOrNull() ?: return@launch
            reviews?.find { it?.client?.id == clientId }?.let { review ->
                withContext(Dispatchers.Main) {
                    _oldReview.value = Triple(review.score, review.comment, review.id)
                }
            }
        }
    }


}