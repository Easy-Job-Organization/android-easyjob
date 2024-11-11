package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.services.EditServiceService

interface EditServiceRepository {
    suspend fun updateService(id: String, name: String, description: String, price: Double): Boolean
}

class EditServiceRepositoryImpl(
    private val service: EditServiceService = EditServiceService.create()
) : EditServiceRepository {
    override suspend fun updateService(id: String, name: String, description: String, price: Double): Boolean {
        return service.updateService(id, mapOf(
            "name" to name,
            "description" to description,
            "price" to price
        )).isSuccessful
    }
}