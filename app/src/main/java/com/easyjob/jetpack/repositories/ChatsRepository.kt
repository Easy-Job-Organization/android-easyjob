package com.easyjob.jetpack.repositories

import android.util.Log
import com.easyjob.jetpack.data.store.UserPreferencesRepository
import com.easyjob.jetpack.services.ChatsService
import com.easyjob.jetpack.services.GroupChatChatsResponse
import com.easyjob.jetpack.services.GroupChatResponse
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


interface ChatsRepository {
    suspend fun retrieveGroupChats(): Response<List<GroupChatResponse>>
    suspend fun retrieveChatsClientProfessional(professionalId: String): Response<GroupChatChatsResponse>
}

class ChatsRepositoryImpl @Inject constructor(
    private val chatsService: ChatsService,
    private val userPreferencesRepository: UserPreferencesRepository
) : ChatsRepository {

    override suspend fun retrieveGroupChats(): Response<List<GroupChatResponse>> {

        val userId = userPreferencesRepository.userIdFlow.first()
        if(userId ==null) {
            Log.e("GroupChatRepository", "User ID is null")
            val errorBody = ResponseBody.create("application/json".toMediaTypeOrNull(),
                """{ "error": "User ID is missing" }"""
            )
            return Response.error(400, errorBody)
        }
        return chatsService.getGroupChatsByUser(userId)
    }

    override suspend fun retrieveChatsClientProfessional(professionalId: String): Response<GroupChatChatsResponse> {
        val userId = userPreferencesRepository.userIdFlow.first()
        if(userId ==null) {
            Log.e("GroupChatRepository", "User ID is null")
            val errorBody = ResponseBody.create("application/json".toMediaTypeOrNull(),
                """{ "error": "User ID is missing" }"""
            )
            return Response.error(400, errorBody)
        }
        return chatsService.getChatsClientProfessional(userId, professionalId)
    }


}