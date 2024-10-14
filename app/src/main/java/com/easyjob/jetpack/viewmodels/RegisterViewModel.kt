package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.repositories.AuthRepository
import com.easyjob.jetpack.repositories.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    val repo: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    //0. Idle
    //1. Loading
    //2. Error
    //3. Success
    val authState = MutableLiveData(0)

    fun signUp(
        name: String,
        last_name: String,
        email: String,
        phone_number: String,
        password: String,
        option: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("RegisterViewModel", "Starting sign-up process.")
            withContext(Dispatchers.Main) {
                authState.value = 1
                Log.d("RegisterViewModel", "Auth state set to loading.")
            }

            val response = repo.signUp(name, last_name, email, phone_number, password, option)
            Log.e("RegisterViewModel", "$response")

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    authState.value = 3
                    Log.d("RegisterViewModel", "Sign-up successful. Auth state set to success.")
                }
            } else {
                withContext(Dispatchers.Main) {
                    authState.value = 2
                    Log.d("RegisterViewModel", "Sign-up failed. Auth state set to error.")
                    Log.d("RegisterViewModel", "${authState.value}")
                }
            }
        }
    }
}