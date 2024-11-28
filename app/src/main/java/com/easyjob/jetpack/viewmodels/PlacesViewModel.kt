package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.LocationResponseDTO
import com.easyjob.jetpack.repositories.PlacesRepository
import com.easyjob.jetpack.repositories.ReviewClientDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class Location(
    val name: String,
    val longitude: Double,
    val latitude: Double
)

@HiltViewModel
class PlacesViewModel @Inject constructor(
    val userPreferencesRepository: UserPreferencesRepository,
    val placesRepository: PlacesRepository
): ViewModel() {
    val placesProfessional = MutableLiveData<List<LocationResponseDTO>>()
    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    fun retrivePlacesOfProfessional() {
        profileState.value = 1
        viewModelScope.launch(Dispatchers.IO) {
            val professionalId = getUserId();
            val places =  placesRepository.getLocationsByProfessional(professionalId?:"");
            withContext(Dispatchers.Main) {
                placesProfessional.value = places?: listOf()
                profileState.value = 3
            }
        }
    }

    fun savePlaceOfProfessional( name: String, longitude: Double, latitude: Double) {
        profileState.value = 1
        viewModelScope.launch(Dispatchers.IO) {
            val professionalId = getUserId();
            val response = placesRepository.saveLocationByProfessional(professionalId?:"", name, longitude, latitude);
            withContext(Dispatchers.Main) {
                profileState.value = 3
            }
        }
    }
}