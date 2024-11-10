package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repo: ReviewRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    fun submitReview(professionalId: String, score: Double, comment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ReviewViewModel", "Submitting review...")
            val clientId = userPreferencesRepository.userIdFlow.first()
            clientId?.let { client ->
                Log.d("ReviewViewModel", "Client ID: $client")
                repo.submitReview(client, professionalId, score, comment)
            }
        }
    }
}