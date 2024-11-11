package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.repositories.EditServiceRepository
import com.easyjob.jetpack.repositories.EditServiceRepositoryImpl

class EditServiceViewModel(
    private val repository: EditServiceRepository = EditServiceRepositoryImpl()
) : ViewModel() {

    private val _serviceName = MutableLiveData("")
    val serviceName: LiveData<String> get() = _serviceName

    private val _serviceDescription = MutableLiveData("")
    val serviceDescription: LiveData<String> get() = _serviceDescription

    private val _servicePrice = MutableLiveData("")
    val servicePrice: LiveData<String> get() = _servicePrice

    private val _updateResult = MutableLiveData<Result<Boolean>>()
    val updateResult: LiveData<Result<Boolean>> get() = _updateResult

    fun onServiceNameChange(newName: String) {
        _serviceName.value = newName
    }

    fun onServiceDescriptionChange(newDescription: String) {
        _serviceDescription.value = newDescription
    }

    fun onServicePriceChange(newPrice: String) {
        _servicePrice.value = newPrice
    }

    fun updateService(serviceId: String) {
        viewModelScope.launch {
            val name = serviceName.value ?: ""
            val description = serviceDescription.value ?: ""
            val price = servicePrice.value?.toDoubleOrNull() ?: 0.0

            _updateResult.value = try {
                val success = repository.updateService(serviceId, name, description, price)
                Result.success(success)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
