package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.AppointmentService
import javax.inject.Inject
import android.util.Log
import com.easyjob.jetpack.models.Appointment
import com.easyjob.jetpack.models.AppointmentGet
import com.easyjob.jetpack.viewmodels.CreateAppointmentDTO

interface AppointmentRepository {
    suspend fun fetchDateServicesPick(id: String): List<Service?>
    suspend fun createAppointment(appointment: CreateAppointmentDTO)
    suspend fun getCLientAppointments(id: String): List<Appointment>
    suspend fun getProfessionalAppointments(id: String): List<Appointment>

}


class AppointmentRepositoryImpl @Inject constructor(
    private val appointmentService: AppointmentService
): AppointmentRepository {



    override suspend fun fetchDateServicesPick(id: String): List<Service?> {
        val res = appointmentService.getServicesOfProfessional(id)
        Log.e(">>>", "The response is: ${res}")
        return res
    }

    override suspend fun createAppointment(appointment: CreateAppointmentDTO) {
        try {
            val response = appointmentService.createAppointment(appointment.client, appointment.professional?:"", appointment)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getCLientAppointments(id: String): List<Appointment> {
        val res = appointmentService.getClientAppointments(id)
        Log.e("AppointmentDetailsScreen", "APPOINTMENTaa: ${res.body()}")
        res.body()?.let {
            return it
        } ?: run {
            return emptyList()
        }

    }

    override suspend fun getProfessionalAppointments(id: String): List<Appointment> {
        val res = appointmentService.getProfessionalAppointments(id)
        res.body()?.let {
            return it
        } ?: run {
            return emptyList()
        }
    }

}