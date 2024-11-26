package com.easyjob.jetpack.services

import android.util.Log
import com.easyjob.jetpack.models.Appointment
import com.easyjob.jetpack.models.ClientAppointment
import retrofit2.Response
import retrofit2.Retrofit
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.viewmodels.CreateAppointmentDTO
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface AppointmentService {
    @GET("/professionals/services/{id}")
    suspend fun getServicesOfProfessional(@Path("id") id: String): List<Service?>

    @GET("/appointment/client/{id}")
    suspend fun getClientAppointments(@Path("id") id: String): Response<List<Appointment>>

    @GET("/appointment/professional/{id}")
    suspend fun getProfessionalAppointments(@Path("id") id: String): Response<List<Appointment>>


    @Headers("Content-Type: application/json")
    @POST("/appointment/{cid}/{pid}")
    suspend fun createAppointment(@Path("cid") cid: String, @Path("pid") pid: String, @Body date: CreateAppointmentDTO): Response<Any>
}

class AppointmentServiceImpl : AppointmentService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: AppointmentService = retrofit.create(AppointmentService::class.java)

    override suspend fun getServicesOfProfessional(id: String): List<Service?> {
        val res = apiService.getServicesOfProfessional(id)
        return res
    }

    override suspend fun getClientAppointments(id: String): Response<List<Appointment>> {
        val res = apiService.getClientAppointments(id)
        return res
    }

    override suspend fun getProfessionalAppointments(id: String): Response<List<Appointment>> {
        val res = apiService.getProfessionalAppointments(id)
        return res
    }

    override suspend fun createAppointment(
        cid: String,
        pid: String,
        appointmentDto: CreateAppointmentDTO
    ) :Response<Any> {
        val formattedDate = formatDate(appointmentDto.date)
        appointmentDto.date = formattedDate
        val res = apiService.createAppointment(cid, pid, appointmentDto)
        if (res.isSuccessful) {
            Log.e("DEBUG", "Cita creada con éxito: ${res.body()}")
        } else {
            Log.e("ERROR", "Error al crear cita: ${res.code()} - ${res.errorBody()?.string()}")
        }

        return res
    }


    fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate: Date = inputFormat.parse(date) ?: return ""
        return outputFormat.format(parsedDate)
    }
}