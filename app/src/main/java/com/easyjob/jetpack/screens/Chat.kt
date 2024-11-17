package com.easyjob.jetpack.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.services.Chat
import com.easyjob.jetpack.ui.theme.components.SendMessageButton
import com.easyjob.jetpack.ui.theme.components.TextChatBar
import com.easyjob.jetpack.ui.theme.components.TopbarChat
import com.easyjob.jetpack.viewmodels.ChatViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chat(
    nestedNavController: NavController = rememberNavController(),
    idProfessional: String,
    chatsViewModel: ChatViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        chatsViewModel.loadMessages(idProfessional)
        chatsViewModel.initializeSocket(idProfessional)
    }

    val professional by chatsViewModel.professional.observeAsState()
    val client by chatsViewModel.client.observeAsState()
    val profileState by chatsViewModel.profileState.observeAsState(0)

    val messages by chatsViewModel.chats.observeAsState(emptyList())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        topBar = {

            var userName = ""

            if (professional?.name != null && professional?.last_name != null) {
                userName = "${professional?.name} ${professional?.last_name}"
            } else if (client?.name != null && client?.last_name != null) {
                userName =  "${client?.name} ${client?.last_name}"
            }

            TopbarChat(
                title = userName,
                scrollBehavior = scrollBehavior,
                navController = nestedNavController,
                imageUrl = professional?.photo_url ?: client?.photo_url ?: "",
                phoneNumber = professional?.phone_number?: client?.phone_number ?: ""
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        when (profileState) {
            1 -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            2 -> {
                Text("Hubo un error al los chats del perfil", color = Color.Red)
            }
            3 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        reverseLayout = true
                    ) {
                        items(messages?: emptyList()) { message ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = if (message.client == null) Alignment.CenterEnd else Alignment.CenterStart // Right for professional, left for client
                            ) {
                                MessageBubble(
                                    message = message
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextChatBar(
                            hint = "Escribe aquí",
                            width = 290
                        )

                        SendMessageButton()
                    }
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: Chat) {

    val isProfessional = (message.professional != null)

    val formattedDate = remember(message.createdAt) {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time

        if (message.createdAt >= today) {
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            timeFormat.format(message.createdAt) // Only show the time for today
        } else {
            val dateFormat = SimpleDateFormat("dd/MM/yy, HH:mm", Locale.getDefault())
            dateFormat.format(message.createdAt) // Show full date for other days
        }
    }

    Column(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(
                color = if (isProfessional) Color.Blue else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp) // Padding inside the bubble
    ) {
        Text(
            text = message.message,
            color = if (isProfessional) Color.White else Color.Black
        )

        Text(
            text = formattedDate,
            color = Color.Gray,
            style = androidx.compose.ui.text.TextStyle(fontSize = 12.sp),
            modifier = Modifier.align(Alignment.End) // Align the date to the end
        )
    }
}
