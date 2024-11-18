package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.AppointmentService
import javax.inject.Inject
import android.util.Log
import com.easyjob.jetpack.models.Appointment
import com.easyjob.jetpack.models.AppointmentGet

interface AppointmentRepository {
    suspend fun fetchDateServicesPick(id: String): List<Service?>
    suspend fun createAppointment(appointment: Appointment)
    suspend fun getCLientAppointments(id: String): List<AppointmentGet>
}


class AppointmentRepositoryImpl @Inject constructor(
    private val appointmentService: AppointmentService
): AppointmentRepository {
    override suspend fun fetchDateServicesPick(id: String): List<Service?> {
        val res = appointmentService.getServicesOfProfessional(id)
        Log.e(">>>", "The response is: ${res}")
        return res
    }

    override suspend fun createAppointment(appointment: Appointment) {       try {
            val response = appointmentService.createAppointment(appointment.client, appointment.professional?:"", appointment)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCLientAppointments(id: String): List<AppointmentGet> {
        val res = appointmentService.getClientAppointments(id)
        if (res.body()!!.appointments.isEmpty()){
            Log.e("HOLII", "VACIO")
            return emptyList()
        }else{
            Log.e("HOLII", "aaaa ${res.body()!!.appointments}")
            return res.body()!!.appointments
        }

    }

}