package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.EditServicesService
import javax.inject.Inject

interface EditServicesRepository {
    suspend fun getServicesOfProfessional(id: String): List<Service?>
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
}