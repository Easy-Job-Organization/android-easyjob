package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.repositories.EditServiceRepository
import com.easyjob.jetpack.repositories.EditServiceRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


@HiltViewModel
class EditServiceViewModel  @Inject constructor(
    private val repository: EditServiceRepository,
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

            Log.d("EditServiceViewModel", "Intentando actualizar el servicio con ID: $serviceId")
            _updateResult.value = try {
                val success = repository.updateService(serviceId, name, description, price)
                Log.d("EditServiceViewModel", "Actualización exitosa: $success")
                Result.success(success)
            } catch (e: Exception) {
                Log.e("EditServiceViewModel", "Error al actualizar el servicio", e)
                Result.failure(e)
            }
        }
    }

    // Método para cargar el servicio actual
    fun fetchServiceById(serviceId: String) {
        Log.d("EditServiceViewModel", "Iniciando fetch del servicio con ID: $serviceId")
        viewModelScope.launch {
            try {
                val response = repository.getService(serviceId)
                Log.d("EditServiceViewModel", "Respuesta de servicio: $response")

                if (response.isSuccessful) {
                    response.body()?.let { service ->
                        _serviceName.value = service.title
                        _serviceDescription.value = service.description
                        _servicePrice.value = service.price
                    }
                } else {
                    Log.e("EditServiceViewModel", "Error al cargar el servicio: $response")
                }
            } catch (e: Exception) {
                Log.e("EditServiceViewModel", "Excepción al cargar el servicio", e)
            }
        }
    }
}
