package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.SpecialitiesResponse
import com.easyjob.jetpack.repositories.ProfessionalProfileRepository
import com.easyjob.jetpack.repositories.ProfessionalProfileRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfessionalProfileViewModel @Inject constructor(
    private val repo: ProfessionalProfileRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    val professionalProfile = MutableLiveData<Professional>()
    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success
    val commentsCount = MutableLiveData<Int>()
    val specialities = MutableLiveData<List<SpecialitiesResponse>>()

    fun loadProfessionalProfile() {

        viewModelScope.launch(Dispatchers.IO) {
            val id = getUserId()

            withContext(Dispatchers.Main) {
                profileState.value = 1 // Loading
            }

            val response = repo.fetchProfessionalProfile(id!!)

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    professionalProfile.value = response.body()
                    profileState.value = 3 // Success
                }
            } else {
                withContext(Dispatchers.Main) {
                    profileState.value = 2 // Error
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                userPreferencesRepository.clearUserInfo()
            }
        }

    }

    fun loadCommentsCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = getUserId()
            val count = repo.fetchReviews(id!!)
            withContext(Dispatchers.Main) {
                commentsCount.value = count
            }
        }
    }

    fun loadSpecialities() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = getUserId()
            val specialitiesList = repo.fetchSpecialities(id!!)
            withContext(Dispatchers.Main) {
                specialities.value = specialitiesList
            }
        }
    }
}
