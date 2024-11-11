package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.DateService
import javax.inject.Inject
import android.util.Log
import com.easyjob.jetpack.models.CreateAppointment
import retrofit2.Response

interface DateRepository {
    suspend fun fetchDateServicesPick(id: String): List<Service?>
    suspend fun createAppointment(appointment: CreateAppointment)
}

class DateRepositoryImpl @Inject constructor(
    private val dateService: DateService
): DateRepository {
    override suspend fun fetchDateServicesPick(id: String): List<Service?> {
        val res = dateService.getServicesOfProfessional(id)
        Log.e(">>>", "The response is: ${res}")
        return res
    }

    override suspend fun createAppointment(appointment: CreateAppointment) {
        try {
            val response = dateService.createAppointment(appointment.client, appointment.professional?:"", appointment)
        } catch (e: Exception) {
            throw e
        }
    }

}