package com.easyjob.jetpack.viewmodels

import android.util.Log
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


data class CreateServiceDTO(
    val title: String,
    val description: String,
    val price: Double
)

@HiltViewModel
class CreateServiceViewModel @Inject constructor(
    private val repository: CreateServiceRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {


    private val _serviceName = MutableLiveData("")
    val serviceName: LiveData<String> get() = _serviceName

    private val _serviceDescription = MutableLiveData("")
    val serviceDescription: LiveData<String> get() = _serviceDescription

    private val _servicePrice = MutableLiveData(0.0)
    val servicePrice: LiveData<Double> get() = _servicePrice

    private val _updateResult = MutableLiveData<Result<Boolean>>()
    val updateResult: LiveData<Result<Boolean>> get() = _updateResult

    fun onServiceNameChange(newName: String) {
        _serviceName.value = newName
    }

    fun onServiceDescriptionChange(newDescription: String) {
        _serviceDescription.value = newDescription
    }

    fun onServicePriceChange(newPrice: Double) {
        _servicePrice.value = newPrice
    }

    fun updateService(serviceId: String) {
        viewModelScope.launch {
            val name = serviceName.value ?: ""
            val description = serviceDescription.value ?: ""
            val price = servicePrice.value ?: 0.0

            Log.d("CreateServiceViewModel", "Intentando actualizar el servicio con ID: $serviceId")
            _updateResult.value = try {
                val success = repository.updateService(serviceId, name, description, price)
                Log.d("CreateServiceViewModel", "Actualización exitosa: $success")
                Result.success(success)
            } catch (e: Exception) {
                Log.e("CreateServiceViewModel", "Error al actualizar el servicio", e)
                Result.failure(e)
            }
        }
    }

    private val _allServices = MutableLiveData<List<Service>>()
    val allServices: LiveData<List<Service>> get() = _allServices

    /*private val _currentService = MutableStateFlow<Service?>(null)
    val currentService: StateFlow<Service?> = _currentService.asStateFlow()*/

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    fun createServiceForProfessional(createServiceDTO: CreateServiceDTO) {
        viewModelScope.launch {
            val professionalId = getUserId()
            repository.createService(professionalId?:"", createServiceDTO)
        }
    }
}