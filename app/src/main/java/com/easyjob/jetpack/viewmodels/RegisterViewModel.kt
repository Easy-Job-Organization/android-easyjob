package com.easyjob.jetpack.viewmodels

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.AuthRepository
import com.easyjob.jetpack.repositories.AuthRepositoryImpl
import com.easyjob.jetpack.services.AuthServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

class RegisterViewModel(
    private val userPreferencesRepository: UserPreferencesRepository, // Add this line
    val repo: AuthRepository = AuthRepositoryImpl(
        authService = AuthServiceImpl(),
        userPreferencesRepository = userPreferencesRepository
    )
) : ViewModel() {

    //0. Idle
    //1. Loading
    //2. Error
    //3. Success
    val authState = MutableLiveData(0)

    fun signUpClient(
        name: String,
        last_name: String,
        email: String,
        phone_number: String,
        password: String,
        option: String,
        uri: Uri,
        contentResolver: ContentResolver
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("RegisterViewModel", "Starting client sign-up process.")
            withContext(Dispatchers.Main) {
                authState.value = 1 // Set loading state
                Log.d("RegisterViewModel", "Auth state set to loading.")
            }

            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val bitmap: Bitmap? = BitmapFactory.decodeStream(inputStream)

            if (bitmap != null) {
                val response =
                    repo.signUpClient(
                        name,
                        last_name,
                        email,
                        phone_number,
                        password,
                        option,
                        uri,
                        contentResolver
                    )

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        authState.value = 3 // Set success state
                        Log.d("RegisterViewModel", "Sign-up successful. Auth state set to success.")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        authState.value = 2 // Set error state
                        Log.d("RegisterViewModel", "Sign-up failed. Auth state set to error.")
                        Log.d("RegisterViewModel", "${authState.value}")
                    }
                }
            } else {
                Log.e("UploadImage", "Bitmap is null, cannot proceed with sign-up.")
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Set error state
                }
            }
        }
    }

    fun signUpProfessional(
        name: String,
        last_name: String,
        email: String,
        phone_number: String,
        password: String,
        option: String,
        uri: Uri,
        service_id: String,
        language_id: String,
        city_id: String,
        speciality_id: String,
        contentResolver: ContentResolver
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("RegisterViewModel", "Starting professional sign-up process.")
            withContext(Dispatchers.Main) {
                authState.value = 1 // Set loading state
                Log.d("RegisterViewModel", "Auth state set to loading.")
            }

            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val bitmap: Bitmap? = BitmapFactory.decodeStream(inputStream)

            if (bitmap != null) {
                val response =
                    repo.signUpProfessional(
                        name,
                        last_name,
                        email,
                        phone_number,
                        password,
                        option,
                        uri,
                        service_id,
                        language_id,
                        city_id,
                        speciality_id,
                        contentResolver
                    )

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        authState.value = 3 // Set success state
                        Log.d("RegisterViewModel", "Sign-up successful. Auth state set to success.")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        authState.value = 2 // Set error state
                        Log.d("RegisterViewModel", "Sign-up failed. Auth state set to error.")
                        Log.d("RegisterViewModel", "${authState.value}")
                    }
                }
            } else {
                Log.e("UploadImage", "Bitmap is null, cannot proceed with sign-up.")
                withContext(Dispatchers.Main) {
                    authState.value = 2 // Set error state
                }
            }
        }
    }
}