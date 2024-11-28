package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Appointment
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AppointmentDetailsService {

    @GET("/appointment/{id}")
    suspend fun getAppointment(
        @Path("id") id: String,
    ): Response<Appointment>


    @PATCH("/appointment/status/{id}/{status_name}")
    suspend fun updateStatus(
        @Path("id") id: String,
        @Path("status_name") status_name: String,
    ): Response<Unit>
}
