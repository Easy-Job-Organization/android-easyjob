package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Appointment
import com.easyjob.jetpack.models.AppointmentGet
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.repositories.AppointmentRepository
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val repo: AppointmentRepository
): ViewModel(){

    val professionalServices = MutableLiveData<List<Service?>>()
    val appointments = MutableLiveData<List<Appointment>>()
    val role = MutableLiveData<String>()
    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success



    suspend fun getRole(): String? {
        return userPreferencesRepository.rolesFlow.firstOrNull()?.get(0)
    }

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    fun loadProfessionalServices(id:String){

        viewModelScope.launch(Dispatchers.IO) {

            val results = repo.fetchDateServicesPick(id)
            val userRole = getRole()
            withContext(Dispatchers.Main){
                role.value = userRole?:""
                professionalServices.value = results
                profileState.value = 3 // Success
            }
        }
    }

    fun loadAppointments() {

        profileState.value = 1

        viewModelScope.launch(Dispatchers.IO) {
            val id = getUserId()
            if (getRole().equals("client")) {
                val res = repo.getCLientAppointments(id?:"")
                withContext(Dispatchers.Main) {
                    appointments.value = res
                    profileState.value = 3 // Success
                }
            } else {
                val res = repo.getProfessionalAppointments(id?:"")
                withContext(Dispatchers.Main){
                    appointments.value = res
                    profileState.value = 3 // Success
                }
            }
        }
    }


    fun createDate(nwAppointment: CreateAppointmentDTO){
        Log.e("AYUDAMEEEEEEEEEEEEE", nwAppointment.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val id = getUserId()
            nwAppointment.client = id?:""
            repo.createAppointment(nwAppointment)
            Log.e("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx", "The response is: ${nwAppointment}")
        }
    }
}

data class CreateAppointmentDTO (
    var client: String,
    var professional: String,
    var date: String,
    val location: String,
    val hour: String,
    val service: String,
    val paymentMethod: String? = null
)

