package com.easyjob.jetpack.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.repositories.ChatsRepository
import com.easyjob.jetpack.repositories.ProfileRepository
import com.easyjob.jetpack.services.Chat
import com.easyjob.jetpack.services.SendMessageDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class UserBasicInfo (
    val userName: String,
    val imageUrl: String
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatsRepository,
    private val profileRepository: ProfileRepository
): ViewModel() {

    private var chatRoomId = "";

    val professional = MutableLiveData<Professional?>();
    val client = MutableLiveData<Client?>();

    val chats = MutableLiveData<List<Chat>>();

    val profileState = MutableLiveData<Int>() // 0: Idle, 1: Loading, 2: Error, 3: Success

    fun initializeSocket(professionalId: String) {
        chatRepository.initializeSocket()
        viewModelScope.launch(Dispatchers.IO) {
            val chatroomResponse = chatRepository.retrieveChatsClientProfessional(professionalId);
            chatRoomId = chatroomResponse?.body()?.id.toString()
            connectSocket()
            listenToMessages()
        }
    }

    fun connectSocket() {
        chatRepository.connect()
    }

    fun disconnectSocket() {
        chatRepository.disconnect()
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("ENVIANDO VIEWMODEL", message)
            chatRepository.sendMessage(message, chatRoomId)
        }
    }

    fun listenToMessages() {
        chatRepository.listen(chatRoomId) { newMessage ->
            addMessage(newMessage)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnectSocket()
    }


    fun loadMessages(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                profileState.value = 1 // Loading
            }
            val chatsResponse = chatRepository.retrieveChatsClientProfessional(id);
            val clientResponse = profileRepository.fetchProfileClient(id);
            val professionalResponse = profileRepository.fetchProfileProfessional(id);

            if (chatsResponse.isSuccessful) {
                withContext(Dispatchers.Main) {
                    if(chatsResponse.body() == null) {
                        chats.value = listOf()
                    } else {
                        chats.value = chatsResponse.body()!!.chats

                        profileState.value = 3 // Success
                    }

                    if(clientResponse.isSuccessful) {
                        client.value = clientResponse.body();
                    }

                    if(professionalResponse.isSuccessful) {
                        professional.value = professionalResponse.body();
                    }

                }
            } else {
                withContext(Dispatchers.Main) {
                    profileState.value = 2 // Error
                }
            }
        }
    }

    private fun addMessage(message: Chat) {
        val currentMessages = chats.value ?: emptyList()
        val updatedMessages = currentMessages + message
        chats.postValue(updatedMessages) // Use postValue here
    }


}