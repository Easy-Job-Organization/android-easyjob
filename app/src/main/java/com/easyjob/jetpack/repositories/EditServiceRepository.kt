package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.EditServiceService
import com.easyjob.jetpack.services.EditServiceServiceImpl
import retrofit2.Response

interface EditServiceRepository {
    suspend fun updateService(id: String, name: String, description: String, price: Double): Boolean

    suspend fun getService(id: String): Response<Service>
}

class EditServiceRepositoryImpl(
    private val editServiceService: EditServiceService = EditServiceServiceImpl()
) : EditServiceRepository {
    override suspend fun updateService(id: String, name: String, description: String, price: Double): Boolean {
        return editServiceService.updateService(id, mapOf(
            "name" to name,
            "description" to description,
            "price" to price
        )).isSuccessful
    }

    override suspend fun getService(id: String): Response<Service> {
        Log.d("EditServiceRepository", "+++++++++ Entra al repository - ${id}")
        val res = editServiceService.getService(id)
        Log.d("EditServiceRepository", "+++++++++ Response - ${res}")
        if (res != null) {
            return res
        } else {
            return res
        }
    }
}