package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.EditServicesService
import retrofit2.Response
import javax.inject.Inject

interface EditServicesRepository {
    suspend fun getServicesOfProfessional(id: String): List<Service?>

    suspend fun deleteService(id: String, serviceID : String): Response<Unit>
}

class EditServicesRepositoryImpl @Inject constructor(
    private val editServicesService: EditServicesService
) : EditServicesRepository {


    override suspend fun getServicesOfProfessional(id: String): List<Service?> {
        val res = editServicesService.getServicesOfProfessional(id)
        Log.e(">>>", "The response is: ${res}")
        if (res != null) {
            return res
        } else {
            return res
        }
    }


    override suspend fun deleteService(id: String, serviceID : String): Response<Unit> {
        val res = editServicesService.deleteService(id, serviceID)
        Log.e(">>>", "<<<The response is: ${res}")
        if (res != null) {
            return res
        } else {
            return res
        }
    }
}