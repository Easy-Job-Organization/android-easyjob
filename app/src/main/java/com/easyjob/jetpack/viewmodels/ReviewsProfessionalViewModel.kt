package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.ReviewClientDTO
import com.easyjob.jetpack.repositories.ReviewProfessionalDTO
import com.easyjob.jetpack.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReviewsProfessionalViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val reviewRepository: ReviewRepository
): ViewModel() {

    val reviewsProfessional = MutableLiveData<List<ReviewProfessionalDTO>>()

    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    fun loadReviews() {
        profileState.value = 1
        viewModelScope.launch(Dispatchers.IO) {

            //Just considering the client side not the professional
            val id = getUserId()

            id?.let {
                val reviews = reviewRepository.getReviewsByProfessionalId(it)
                withContext(Dispatchers.Main) {
                    reviewsProfessional.value = reviews.body()
                    profileState.value = 3
                }
            }
        }

    }
}