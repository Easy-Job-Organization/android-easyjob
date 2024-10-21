package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.repositories.ProfessionalProfileRepository
import com.easyjob.jetpack.repositories.ProfessionalProfileRepositoryImpl
import com.easyjob.jetpack.services.ProfessionalProfileResponse
import com.easyjob.jetpack.services.SpecialitiesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfessionalProfileViewModel(
    private val repo: ProfessionalProfileRepository = ProfessionalProfileRepositoryImpl()
) : ViewModel() {

    val professionalProfile = MutableLiveData<ProfessionalProfileResponse>()
    val city = MutableLiveData<String>()
    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success
    val commentsCount = MutableLiveData<Int>()
    val specialities = MutableLiveData<List<SpecialitiesResponse>>()

    fun loadProfessionalProfile(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                profileState.value = 1 // Loading
            }

            val response = repo.fetchProfessionalProfile(id)

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

    fun loadCity(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val cityResponse = repo.fetchCities(id)

            withContext(Dispatchers.Main) {
                city.value = cityResponse?.name ?: "Ciudad desconocida"
            }
        }
    }

    fun loadCommentsCount(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val count = repo.fetchReviews(id)
            withContext(Dispatchers.Main) {
                commentsCount.value = count
            }
        }
    }

    fun loadSpecialities(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val specialitiesList = repo.fetchSpecialities(id)
            withContext(Dispatchers.Main) {
                specialities.value = specialitiesList
            }
        }
    }
}
