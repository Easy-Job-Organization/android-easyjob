package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor (
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    //0 - Idle
    //1 - Logged before
    //2 - Not logged before
    val authState = MutableLiveData(0)

    fun checkLoggedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            //sleep(2000)
            sleep(1000)
            val isLoggedIn = userPreferencesRepository.userIdFlow.first()
            val isRole = userPreferencesRepository.rolesFlow.first()
            Log.e("SplashViewModel", "User ID: $isLoggedIn")
            Log.e("SplashViewModel", "Role: $isRole")
            withContext(Dispatchers.Main){
                if(isLoggedIn != null) {
                    if(isRole.contains("client")) {
                        authState.value = 1
                    } else {
                        authState.value = 2
                    }
                } else {
                    authState.value = 3
                }
            }
        }

    }

    fun updateAuthState(newState: Int) {
        authState.value = newState
    }

}