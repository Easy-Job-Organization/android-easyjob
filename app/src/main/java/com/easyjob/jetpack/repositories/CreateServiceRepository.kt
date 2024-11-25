package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.CreateServiceService
import com.easyjob.jetpack.viewmodels.CreateServiceDTO
import retrofit2.Response
import javax.inject.Inject

interface CreateServiceRepository {
    suspend fun createService(userId: String, createServiceDTO: CreateServiceDTO): Response<Service>

    suspend fun updateService(id: String, name: String, description: String, price: Double): Boolean

    suspend fun getAllServices(): List<Service>
}

class CreateServiceRepositoryImpl @Inject constructor(
    private val createServiceService: CreateServiceService
) : CreateServiceRepository {

    override suspend fun updateService(id: String, name: String, description: String, price: Double): Boolean {
        val updates = mapOf<String, Any>(
            "name" to name,
            "description" to description,
            "price" to price
        )
        return createServiceService.updateService(id, updates).isSuccessful
    }

    override suspend fun createService(userId: String, createServiceDTO: CreateServiceDTO): Response<Service> {
        val res = createServiceService.createService(createServiceDTO)
        val serviceId = res.body()?.id
        if (serviceId != null) {
            createServiceService.linkProfessionalToService(userId, serviceId)
        }
        return res
    }

    override suspend fun getAllServices(): List<Service> {
        val res = createServiceService.getAllServices()
        Log.e(">>>", "<<<The response is: ${res}")
        if (res != null) {
            return res
        } else {
            return res
        }
    }
}