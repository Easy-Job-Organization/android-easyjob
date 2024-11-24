package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.repositories.EditServicesRepository
import com.easyjob.jetpack.repositories.EditServicesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditServicesViewModel @Inject constructor(
    private val repo: EditServicesRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _services = MutableLiveData<List<Service?>>()
    val services: LiveData<List<Service?>> get() = _services

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String>("")
    val errorMessage: LiveData<String> get() = _errorMessage

    val _deleteSuccess = MutableLiveData<Boolean>()
    val deleteSuccess: LiveData<Boolean> get() = _deleteSuccess

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    fun fetchServicesOfProfessional() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = getUserId()
            withContext(Dispatchers.Main) { _loading.value = true }

            try {
                val response = repo.getServicesOfProfessional(id!!)
                withContext(Dispatchers.Main) { _services.value = response }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { _errorMessage.value = e.message ?: "Error desconocido" }
            } finally {
                withContext(Dispatchers.Main) { _loading.value = false }
            }
        }
    }

    fun deleteService(serviceId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = getUserId()
            withContext(Dispatchers.Main) { _loading.value = true }

            try {
                val response = repo.deleteService(id!!, serviceId)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        _deleteSuccess.value = true
                        _services.value = _services.value?.filterNot { it?.id == serviceId }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _errorMessage.value = "Error al eliminar el servicio: ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message ?: "Error desconocido"
                }
            } finally {
                withContext(Dispatchers.Main) { _loading.value = false }
            }
        }
    }

}
