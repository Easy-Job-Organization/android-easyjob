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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val repo: AppointmentRepository
): ViewModel(){

    val professionalServices = MutableLiveData<List<Service?>>()
    val pendingAppointments = MutableLiveData<List<Appointment>>()
    val acceptedAppointments = MutableLiveData<List<Appointment>>()
    val cancelledAppointments = MutableLiveData<List<Appointment>>()
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
            withContext(Dispatchers.Main){
                professionalServices.value = results
                profileState.value = 3 // Success
            }
        }
    }

    fun loadAppointments() {

        profileState.value = 1


        viewModelScope.launch(Dispatchers.IO) {
            val userRole = getRole()
            val id = getUserId()
            if (getRole().equals("client")) {
                val res = repo.getCLientAppointments(id?:"")
                organizeAppointments(res)
                withContext(Dispatchers.Main) {
                    role.value = userRole?:""
                    profileState.value = 3 // Success
                }
            } else {
                val res = repo.getProfessionalAppointments(id?:"")
                organizeAppointments(res)
                withContext(Dispatchers.Main){
                    role.value = userRole?:""
                    profileState.value = 3 // Success
                }
            }
        }
    }

    suspend fun organizeAppointments(appointments: List<Appointment>) {

        val sortedAppointments = appointments.sortedBy { it.date + it.hour }

        val currentDate = Date()

        val (passed, nPassed) = sortedAppointments.partition {
            val appointmentDate = SimpleDateFormat("yyyy-MM-dd").parse(it.date)
            appointmentDate.before(currentDate)
        }

        val (pending, nPending) = nPassed.partition {
            it.appointmentStatus.status == "pendiente"
        }

        val  (accepted, cancelled) = nPending.partition {
            it.appointmentStatus.status == "aceptada"
        }

        val joined = cancelled + passed

        val pendingSorted = pending.sortedBy { it.date + it.hour }
        val acceptedSorted = accepted.sortedBy { it.date + it.hour }
        val pastSorted = joined.sortedBy { it.date + it.hour }.reversed()


        withContext(Dispatchers.Main){
            pendingAppointments.value = pendingSorted
            acceptedAppointments.value = acceptedSorted
            cancelledAppointments.value = pastSorted
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

