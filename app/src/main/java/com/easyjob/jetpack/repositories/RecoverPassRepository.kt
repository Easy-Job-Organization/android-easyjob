package com.easyjob.jetpack.repositories

import com.easyjob.jetpack.services.RecoverPassService
import javax.inject.Inject

interface RecoverPassRepository {
    suspend fun recoverPass(email: String)
}

class RecoverPassRepositoryImpl @Inject constructor(
    private val recoverPassService: RecoverPassService
) : RecoverPassRepository {
    override suspend fun recoverPass(email: String) {
        return recoverPassService.recoverPass(email)
    }
}