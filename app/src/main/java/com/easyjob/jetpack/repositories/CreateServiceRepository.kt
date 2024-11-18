package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.services.CreateServiceService
import retrofit2.Response
import javax.inject.Inject

interface CreateServiceRepository {
    suspend fun createService(id: String, serviceID : String): Response<Unit>

    suspend fun getAllServices(): List<Service>
}

class CreateServiceRepositoryImpl @Inject constructor(
    private val createServiceService: CreateServiceService
) : CreateServiceRepository {



    override suspend fun createService(id: String, serviceID : String): Response<Unit> {
        val res = createServiceService.createService(id, serviceID)
        Log.e(">>>", "<<<The response is: ${res}")
        if (res != null) {
            return res
        } else {
            return res
        }
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