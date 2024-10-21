package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.repositories.ProfileRepository
import com.easyjob.jetpack.repositories.ProfileRepositoryImpl
import com.easyjob.jetpack.services.ProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val repo: ProfileRepository = ProfileRepositoryImpl()
) : ViewModel() {

    val profile = MutableLiveData<ProfileResponse>()
    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success

    fun loadProfile(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                profileState.value = 1 // Loading
            }

            val response = repo.fetchProfile(id)

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    profile.value = response.body()
                    profileState.value = 3 // Success
                }
            } else {
                withContext(Dispatchers.Main) {
                    profileState.value = 2 // Error
                }
            }
        }
    }
}