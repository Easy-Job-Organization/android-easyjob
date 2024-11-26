package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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

    private val _oldReview = MutableStateFlow<Pair<Double, String>?>(null)
    val oldReview: StateFlow<Pair<Double, String>?> = _oldReview

    fun submitReview(professional: String, score: Double, comment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val clientId = userPreferencesRepository.userIdFlow.firstOrNull() ?: return@launch
            _state.value = ReviewState.Loading

            val response = repo.submitReview(clientId, professional, score, comment)
            _state.value = if (response.isSuccessful) {
                ReviewState.Success
            } else {
                ReviewState.Error("Error al enviar la reseña. Inténtalo nuevamente.")
            }
        }
    }

    fun checkSubmittedReview(reviews: List<Review?>?) {
        viewModelScope.launch(Dispatchers.IO) {
            val clientId = userPreferencesRepository.userIdFlow.firstOrNull() ?: return@launch
            val review = reviews?.firstOrNull { it?.client?.id == clientId }
            _oldReview.value = review?.let { Pair(it.score, it.comment) }
        }
    }

}