package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.repositories.ChatsRepository
import com.easyjob.jetpack.repositories.SearchScreenRepository
import com.easyjob.jetpack.repositories.SearchScreenRepositoryImpl
import com.easyjob.jetpack.services.Chat
import com.easyjob.jetpack.services.GroupChatChatsResponse
import com.easyjob.jetpack.services.GroupChatResponse
import com.easyjob.jetpack.services.ProfessionalCardResponse
import com.easyjob.jetpack.services.ProfessionalSearchScreenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo: ChatsRepository,
): ViewModel() {
    val professional = MutableLiveData<Professional?>();
    val client = MutableLiveData<Client>();
    val chats = MutableLiveData<List<Chat>>();

    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success

    fun loadMessages(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                profileState.value = 1 // Loading
            }
            val response = repo.retrieveChatsClientProfessional(id);
            val client = repo

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    if(response.body() == null) {
                        chats.value = listOf()
                    } else {
                        chats.value = response.body()!!.chats
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