package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.models.Review
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.repository.ProfessionalRepository
import com.easyjob.jetpack.repository.ProfessionalRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfessionalViewModel(
    val repo: ProfessionalRepository = ProfessionalRepositoryImpl()
): ViewModel() {

    private val _professional = MutableLiveData<Professional?>()
    val professional: LiveData<Professional?> get() = _professional

    private val _servicesOfProfessional = MutableLiveData<List<Service?>>()
    val services: LiveData<List<Service?>> get() = _servicesOfProfessional

    private val _reviewsOfProfessional = MutableLiveData<List<Review?>>()
    val reviews: LiveData<List<Review?>> get() = _reviewsOfProfessional

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String>("")
    val errorMessage: LiveData<String> get() = _errorMessage


    fun fetchProfessional(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("SearchViewModel", "Starting")
            withContext(Dispatchers.Main) {
                _loading.value = true
                Log.d("SearchViewModel", "Starting")
            }

            try {
                val response = repo.getProfessional(id)
                withContext(Dispatchers.Main) {
                    Log.e("SearchViewModel", "${response}")
                    _professional.value = response
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

    fun fetchServicesOfProfessinal(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("SearchViewModel", "Starting")
            withContext(Dispatchers.Main) {
                _loading.value = true
                Log.d("SearchViewModel", "Starting")
            }

            try {
                val response = repo.getServicesOfProfessional(id)
                withContext(Dispatchers.Main) {
                    Log.e("SearchViewModel", "${response}")
                    _servicesOfProfessional.value = response
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

    fun fetchReviewsOfProfessinal(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("SearchViewModel", "Starting")
            withContext(Dispatchers.Main) {
                _loading.value = true
                Log.d("SearchViewModel", "Starting")
            }

            try {
                val response = repo.getReviewOfProfessional(id)
                withContext(Dispatchers.Main) {
                    Log.e("SearchViewModel", "${response}")
                    _reviewsOfProfessional.value = response
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