package com.easyjob.jetpack.services

import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

data class GroupChatResponse(
     val id: String,
     val name: String,
     val client: Client?,
     val professional: Professional?
 )

data class Chat (
    val id: String,
    val message: String,
    val createdAt: Date,
    val client: Client?,
    val professional: Professional?
)

data class GroupChatChatsResponse (
    val id: String,
    val name: String,
    val chats: List<Chat>
)

interface ChatsService {
    @GET("chats/group_chats/{user_id}")
    suspend fun getGroupChatsByUser(@Path("user_id") userId: String): Response<List<GroupChatResponse>>


    @GET("chats/{client_id}/{professional_id}")
    suspend fun getChatsClientProfessional(
        @Path("client_id") clientId: String,
        @Path("Professional_id") professionalId: String): Response<GroupChatChatsResponse>
}

class ChatsServiceImpl : ChatsService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.easyjob.com.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ChatsService = retrofit.create(ChatsService::class.java)

    override suspend fun getGroupChatsByUser(userId: String): Response<List<GroupChatResponse>> {
        return this.apiService.getGroupChatsByUser(userId);
    }

    override suspend fun getChatsClientProfessional(
        clientId: String,
        professionalId: String
    ): Response<GroupChatChatsResponse> {
        return this.apiService.getChatsClientProfessional(clientId, professionalId)
    }

}