package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.repositories.EditServicesRepository
import com.easyjob.jetpack.repositories.EditServicesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditServicesViewModel(private val repo: EditServicesRepository = EditServicesRepositoryImpl()) :
    ViewModel() {
    private val _services = MutableLiveData<List<Service?>>()
    val services: LiveData<List<Service?>> get() = _services

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String>("")
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchServicesOfProfessinal(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("EditServicesViewModel", "Starting")
            withContext(Dispatchers.Main) {
                _loading.value = true
                Log.d("EditServicesViewModel", "Starting")
            }

            try {
                val response = repo.getServicesOfProfessional(id)
                withContext(Dispatchers.Main) {
                    Log.e("EditServicesViewModel", "${response}")
                    _services.value = response
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message ?: "Error desconocido"
                }
            } finally {
                withContext(Dispatchers.Main) {
                    _loading.value = false
                }
            }
        }
    }
}