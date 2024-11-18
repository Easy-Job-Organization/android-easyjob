package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repo: ReviewRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // 0. Idle
    // 1. Loading
    // 2. Success
    // 3. Error
    val state = MutableLiveData(0)

    fun submitReview(professional: String, score: Double, comment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val clientId = userPreferencesRepository.userIdFlow.first()
            clientId?.let { client ->
                Log.d("ReviewViewModel", "Client ID: $client")
                Log.d("ReviewViewModel", "Professional ID: $professional")

                withContext(Dispatchers.Main) {
                    state.value = 1
                    Log.d("ReviewViewModel", "Submitting review...")
                }

                val response = repo.submitReview(client, professional, score, comment)

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        state.value = 2
                        Log.d("ReviewViewModel", "Review submitted successfully.")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        state.value = 3
                        Log.e("ReviewViewModel", "Failed to submit review.")
                    }
                }
            }
        }
    }
}