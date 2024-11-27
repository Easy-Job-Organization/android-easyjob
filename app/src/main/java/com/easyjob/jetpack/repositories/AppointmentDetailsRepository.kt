package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Appointment
import com.easyjob.jetpack.services.AppointmentDetailsService
import retrofit2.Response
import javax.inject.Inject

interface AppointmentDetailsRepository {
    suspend fun getAppointment(id: String): Response<Appointment>

    suspend fun updateStatus(id: String, status_name: String): Response<Unit>

}

class AppointmentDetailsRepositoryImpl @Inject constructor(
    private val appointmentDetailsService: AppointmentDetailsService
) : AppointmentDetailsRepository {
    override suspend fun getAppointment(id: String): Response<Appointment> {
        val res = appointmentDetailsService.getAppointment(id)
        return res
    }


    override suspend fun updateStatus(id: String, status_name: String): Response<Unit> {
        val res = appointmentDetailsService.updateStatus(id, status_name)
        return res
    }

}