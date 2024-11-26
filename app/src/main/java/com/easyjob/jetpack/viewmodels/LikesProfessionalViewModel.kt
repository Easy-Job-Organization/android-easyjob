package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Appointment
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.repositories.LikesProfessionalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LikesProfessionalViewModel @Inject constructor(
    private val likesProfessionalRepository: LikesProfessionalRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    val likedProfessionals = MutableLiveData<List<Professional?>>()

    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    fun loadClientLikes() {
        profileState.value = 1
        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) { }
            val id = getUserId()
            val results = likesProfessionalRepository.fetchLikesClient(id?:"")
            withContext(Dispatchers.Main) {
                likedProfessionals.value = results
                profileState.value = 3 // Success
            }

        }

    }

}