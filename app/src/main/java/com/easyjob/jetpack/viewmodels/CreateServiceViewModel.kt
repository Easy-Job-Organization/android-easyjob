package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.repositories.CreateServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val repository: CreateServiceRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _allServices = MutableLiveData<List<Service>>()
    val allServices: LiveData<List<Service>> get() = _allServices

    private val _currentService = MutableStateFlow<Service?>(null)
    val currentService: StateFlow<Service?> = _currentService.asStateFlow()

    init {
        fetchAllServices()
    }

    fun fetchAllServices() {
        viewModelScope.launch {
            _allServices.value = repository.getAllServices()
        }
    }

    fun setCurrentService(service: Service) {
        _currentService.value = service
    }

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    fun createServiceForProfessional() {
        viewModelScope.launch {
            val professionalId = getUserId()
            val selectedService = _currentService.value
            if (selectedService != null) {
                repository.createService(professionalId!!, selectedService.id)
            }
        }
    }
}