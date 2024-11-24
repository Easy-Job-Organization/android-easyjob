package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.EditServiceService
import retrofit2.Response
import javax.inject.Inject

interface EditServiceRepository {
    suspend fun updateService(id: String, name: String, description: String, price: Double): Boolean

    suspend fun getService(id: String): Response<Service>

}

class EditServiceRepositoryImpl @Inject constructor(
    private val editServiceService: EditServiceService
) : EditServiceRepository {
    override suspend fun updateService(id: String, name: String, description: String, price: Double): Boolean {
        val updates = mapOf<String, Any>(
            "name" to name,
            "description" to description,
            "price" to price
        )
        return editServiceService.updateService(id, updates).isSuccessful
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