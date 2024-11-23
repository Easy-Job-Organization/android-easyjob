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

    val clientAppointments = MutableLiveData<List<AppointmentGet?>>()

    val userFirstRole = MutableLiveData<String>()

    suspend fun getUserId(): String? {
        return userPreferencesRepository.userIdFlow.firstOrNull()
    }

    suspend fun getUserRoles(): List<String> {
        return userPreferencesRepository.rolesFlow.firstOrNull()?: emptyList()
    }

    fun loadProfessionalServices(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            val results = repo.fetchDateServicesPick(id)
            withContext(Dispatchers.Main){
                professionalServices.value = results
            }
        }
    }

    fun loadClientAppointments(){
        viewModelScope.launch(Dispatchers.IO) {
            val roles = getUserRoles()
            Log.e("ROLE", "ROLEEEES ${roles}")
            val id = getUserId()
            if(roles[0]=="client"){
                val res = repo.getCLientAppointments(id?:"")
                withContext(Dispatchers.Main){
                    clientAppointments.value = res
                }
                Log.e("HOLII", "XX ${res}")
            }else{
                val res = repo.getCLientAppointments(id?:"") //Cambiar cuando haya para profesional
                withContext(Dispatchers.Main){
                    clientAppointments.value = res
                }
                Log.e("HOLII", "XX ${res}")
            }
        }
    }

    fun loadUserRole(){
        viewModelScope.launch(Dispatchers.IO) {
            val roles = getUserRoles()
            if(roles.isEmpty()){
                userFirstRole.value = ""
            }else{
                userFirstRole.value  = roles[0]
            }
        }
    }


    fun createDate(nwAppointment: Appointment){
        viewModelScope.launch(Dispatchers.IO) {
            val id = getUserId()
            nwAppointment.client = id?:""
            repo.createAppointment(nwAppointment)
            Log.e("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx", "The response is: ${nwAppointment}")
        }
    }
}

