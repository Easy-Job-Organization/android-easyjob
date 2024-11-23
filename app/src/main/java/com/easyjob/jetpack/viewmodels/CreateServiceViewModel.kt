package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.CreateServiceDTO
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


    private val _serviceName = MutableLiveData("")
    val serviceName: LiveData<String> get() = _serviceName

    private val _serviceDescription = MutableLiveData("")
    val serviceDescription: LiveData<String> get() = _serviceDescription

    private val _servicePrice = MutableLiveData(0.0)
    val servicePrice: LiveData<Double> get() = _servicePrice

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> get() = _updateResult

    fun setUpdateResult (value: Boolean){
        _updateResult.value = value
    }

    fun onServiceNameChange(newName: String) {
        _serviceName.value = newName
    }

    fun onServiceDescriptionChange(newDescription: String) {
        _serviceDescription.value = newDescription
    }

    fun onServicePriceChange(newPrice: Double) {
        _servicePrice.value = newPrice
    }

    private val _allServices = MutableLiveData<List<Service>>()


    private val _currentService = MutableStateFlow<Service?>(null)
    val currentService: StateFlow<Service?> = _currentService.asStateFlow()

    init {
        fetchAllServices()
    }

    private fun fetchAllServices() {
        viewModelScope.launch {
            _allServices.value = repository.getAllServices()
        }
    }

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    fun createServiceForProfessional() {
        viewModelScope.launch {
            val selectedService = _serviceName.value?.let { name ->
                _serviceDescription.value?.let { description ->
                    _servicePrice.value?.let { price ->
                        CreateServiceDTO(name, description, price)
                    }
                }
            }

            if (selectedService == null) {
                _updateResult.value = false
                return@launch
            }

            try {
                val res = repository.createService(selectedService)

                if (res.isSuccessful) {
                    val createdService = res.body()
                    val serviceId = createdService?.id

                    if (serviceId != null) {
                        val professionalId = getUserId()
                        if (professionalId != null) {
                            repository.linkServiceProfessional(professionalId, serviceId)
                            _updateResult.value = true
                        } else {
                            _updateResult.value = false
                            Log.e("CreateServiceViewModel", "El ID del profesional no está disponible.")
                        }
                    } else {
                        _updateResult.value = false
                        Log.e("CreateServiceViewModel", "El ID del servicio no se encontró en la respuesta.")
                    }
                } else {
                    _updateResult.value = false
                    Log.e("CreateServiceViewModel", "Error al crear el servicio: ${res.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _updateResult.value = false
                Log.e("CreateServiceViewModel", "Excepción al crear el servicio", e)
            }
        }
    }

}