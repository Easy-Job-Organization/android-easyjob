package com.easyjob.jetpack.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.easyjob.jetpack.ui.theme.components.RecordChat
import com.easyjob.jetpack.ui.theme.components.TextChatBar
import com.easyjob.jetpack.ui.theme.components.TopbarChat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chat(
    nestedNavController: NavController = rememberNavController(),
    idProfessional: String,
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val nameProfessional = "prueba"

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),

        topBar = {
            TopbarChat(
                title = nameProfessional,
                scrollBehavior = scrollBehavior,
                navController = nestedNavController
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Column {
                    for (i in 1..20) {
                        MessageBubble("Mensaje $i")
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

                RecordChat()

            }

        }

    }

}

@Composable
fun MessageBubble(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = text)
    }
}