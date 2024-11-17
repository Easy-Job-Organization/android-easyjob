package com.easyjob.jetpack.services

import android.util.Log
import com.easyjob.jetpack.models.CreateAppointment
import retrofit2.Response
import retrofit2.Retrofit
import com.easyjob.jetpack.models.Service
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface DateService {
    @GET("/professionals/services/{id}")
    suspend fun getServicesOfProfessional(@Path("id") id: String): List<Service?>

    @Headers("Content-Type: application/json")
    @POST("/appointment/{cid}/{pid}")
    suspend fun createAppointment(@Path("cid") cid: String, @Path("pid") pid: String, @Body date: CreateAppointment): Response<Any>
}

class DateServiceImpl : DateService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: DateService = retrofit.create(DateService::class.java)
    override suspend fun getServicesOfProfessional(id: String): List<Service?> {
        val res = apiService.getServicesOfProfessional(id)
        return res
    }

    override suspend fun createAppointment(
        cid: String,
        pid: String,
        createAppointmentDto: CreateAppointment
    ) :Response<Any> {
        val formattedDate = formatDate(createAppointmentDto.date)
        createAppointmentDto.date = formattedDate
        Log.e("AAAAAAAA", "Antes de enviar a guardar  $formattedDate")
        val res = apiService.createAppointment(cid, pid, createAppointmentDto)
        if (res.isSuccessful) {
            Log.e("DEBUGX", "Cita creada con éxito: ${res.body()}")
        } else {
            Log.e("ERRORX", "Error al crear cita: ${res.code()} - ${res.errorBody()?.string()}")
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