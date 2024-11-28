package com.easyjob.jetpack.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.repositories.ResourcesRepository
import com.easyjob.jetpack.repositories.ResourcesRepositoryImpl
import com.easyjob.jetpack.services.City
import com.easyjob.jetpack.services.Language
import com.easyjob.jetpack.services.Service
import com.easyjob.jetpack.services.Speciality
import kotlinx.coroutines.launch

class ResourcesViewModel(
    private val repo: ResourcesRepository = ResourcesRepositoryImpl()
) : ViewModel() {

    val cities = MutableLiveData<List<City>>()
    val languages = MutableLiveData<List<Language>>()
    val services = MutableLiveData<List<Service>>()
    val specialities = MutableLiveData<List<Speciality>>()

    // Obtener ciudades
    fun getCities() {
        viewModelScope.launch {
            val response = repo.getCities()
            if (response.isSuccessful) {
                cities.postValue(response.body() ?: emptyList())
            } else {
                cities.postValue(emptyList())
            }
        }
    }

    // Obtener lenguajes
    fun getLanguages() {
        viewModelScope.launch {
            val response = repo.getLanguages()
            if (response.isSuccessful) {
                languages.postValue(response.body() ?: emptyList())
            } else {
                languages.postValue(emptyList())
            }
        }
    }

    // Obtener servicios
    fun getServices() {
        viewModelScope.launch {
            val response = repo.getServices()
            if (response.isSuccessful) {
                services.postValue(response.body() ?: emptyList())
            } else {
                services.postValue(emptyList())
            }
        }
    }

    // Obtener especialidades
    fun getSpecialities() {
        viewModelScope.launch {
            val response = repo.getSpecialities()
            if (response.isSuccessful) {
                specialities.postValue(response.body() ?: emptyList())
            } else {
                specialities.postValue(emptyList())
            }
        }
    }
}