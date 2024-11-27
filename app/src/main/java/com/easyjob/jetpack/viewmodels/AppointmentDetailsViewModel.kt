package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Appointment
import com.easyjob.jetpack.repositories.AppointmentDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentDetailsViewModel @Inject constructor(
    private val appointmentDetailsRepository: AppointmentDetailsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _appointment = MutableLiveData<Appointment?>()
    val appointment: LiveData<Appointment?> get() = _appointment

    private val _statusUpdateSuccess = MutableLiveData<Boolean>()
    val statusUpdateSuccess: LiveData<Boolean> get() = _statusUpdateSuccess

    private val _role = MutableLiveData<String>()
    val role: LiveData<String> get() = _role

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    suspend fun getRole(){
        _role.value= userPreferencesRepository.rolesFlow.firstOrNull()?.get(0)
        Log.e("AppointmentDetailsScreen", "ROLE: $_role.value")
    }

    fun loadAppointmentDetails(appointmentId: String) {
        viewModelScope.launch {
            try {
                val response = appointmentDetailsRepository.getAppointment(appointmentId)
                if (response.isSuccessful) {
                    _appointment.value = response.body()
                } else {
                    _error.value = "Error al cargar los detalles de la cita"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateAppointmentStatus(appointmentId: String, statusName: String) {
        viewModelScope.launch {
            try {
                val response = appointmentDetailsRepository.updateStatus(appointmentId, statusName)
                _statusUpdateSuccess.value = response.isSuccessful
                if (!response.isSuccessful) {
                    _error.value = "Error al actualizar el estado de la cita"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

}
