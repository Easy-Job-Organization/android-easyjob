package com.easyjob.jetpack.services

import android.util.Log
import com.easyjob.jetpack.models.Client
import com.easyjob.jetpack.models.Professional
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
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

data class SendMessageDTO (
    val chatroom_id: String,
    val message: String,
    val professional_id: String?,
    val client_id: String?
)

data class reciveMessageDTO (
    val id: String,
    val message: String,
    val createdAt: Date,
    val client: Client?,
    val professional: Professional?
)

interface ChatsService {

    fun initializeSocket(serverUrl: String)

    fun connect()

    fun disconnect()

    fun sendMessage( message: SendMessageDTO )

    fun listen(event: String, callback: (Chat) -> Unit)

    @GET("chats/group_chats/{user_id}")
    suspend fun getGroupChatsByUser(@Path("user_id") userId: String): Response<List<GroupChatResponse>>


    @GET("chats/{client_id}/{professional_id}")
    suspend fun getChatsClientProfessional(
        @Path("client_id") clientId: String,
        @Path("professional_id") professionalId: String): Response<GroupChatChatsResponse>
}

class ChatsServiceImpl : ChatsService {

    private lateinit var socket: Socket

    override fun initializeSocket(serverUrl: String) {
        try {
            socket = IO.socket(serverUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun connect() {
        if (!socket.connected()) {
            socket.connect()
        }
    }

    override fun disconnect() {
        if (socket.connected()) {
            socket.disconnect()
        }
    }

    override fun sendMessage( message: SendMessageDTO ) {
        Log.e("SEND MESSAGE", "Sending message: $message")
        if (socket.connected()) {
            // Convert to JSON string using Gson
            val jsonMessage = Gson().toJson(message)

            // Convert JSON string to JSONObject
            val jsonObject = JSONObject(jsonMessage)
            socket.emit("message", jsonObject)
            Log.e("NOse que poner", jsonMessage.toString())
        } else {
            Log.e("Socket", "Socket is not connected!")
        }
    }

    override fun listen(event: String, callback: (Chat) -> Unit) {
        socket.on(event) { args ->
            if (args.isNotEmpty()) {
                Log.e("LISTEN SERVICE", args.toString())
                val json = args[0].toString() // Convert the first argument to a JSON string
                Log.e("JSONNNN", json)
                try {
                    val chat = Gson().fromJson(json, Chat::class.java)
                    Log.e("CHATTTTTT", chat.toString())
                    callback(chat)
                } catch (e: Exception) {
                    Log.e("Socket Parsing Error", "Error parsing chat object", e)
                }
            }
        }
    }

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