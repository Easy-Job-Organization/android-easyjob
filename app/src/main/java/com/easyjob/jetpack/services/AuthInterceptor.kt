package com.easyjob.jetpack.services

import com.easyjob.jetpack.data.store.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = runBlocking {
            userPreferencesRepository.jwtFlow.first()
        }

        val request = chain.request().newBuilder()
            .apply {
                jwt?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }
            .build()

        return chain.proceed(request)
    }
}
