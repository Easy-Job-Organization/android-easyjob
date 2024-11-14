package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.repositories.ChatsRepository
import com.easyjob.jetpack.repositories.SearchScreenRepository
import com.easyjob.jetpack.repositories.SearchScreenRepositoryImpl
import com.easyjob.jetpack.services.GroupChatResponse
import com.easyjob.jetpack.services.ProfessionalCardResponse
import com.easyjob.jetpack.services.ProfessionalSearchScreenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val repo: ChatsRepository,
): ViewModel() {
    val professionalProfile = MutableLiveData<List<GroupChatResponse>>();
    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success

    fun loadGroupChats() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                profileState.value = 1 // Loading
            }

            val response = repo.retrieveGroupChats()
            Log.e("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", response.body().toString())

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    if(response.body() == null) {
                        professionalProfile.value = listOf()
                    } else {
                        professionalProfile.value = response.body()
                        profileState.value = 3 // Success
                    }

                }
            } else {
                withContext(Dispatchers.Main) {
                    profileState.value = 2 // Error
                }
            }
        }
    }
}