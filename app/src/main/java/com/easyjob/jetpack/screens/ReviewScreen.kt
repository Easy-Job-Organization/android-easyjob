package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyjob.jetpack.viewmodels.ReviewViewModel

@Composable
fun ReviewScreen(
    professionalId: String,
    viewModel: ReviewViewModel = hiltViewModel(),
    onCancel: () -> Unit,
    onReviewSubmitted: () -> Unit
) {
    var score by remember { mutableDoubleStateOf(0.0) }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Califica el servicio", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        // Estrellas de rating
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 1..5) {
                IconButton(onClick = { score = i.toDouble() }) {
                    Icon(
                        imageVector = if (i <= score) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = null,
                        tint = if (i <= score) Color.Yellow else Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para comentarios
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Comentario") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            maxLines = 5,
            singleLine = false
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botones de aceptar y cancelar
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { onCancel() }) {
                Text("Cancelar")
            }
            Button(onClick = {
                // Llama a submitReview del ViewModel
                viewModel.submitReview(professionalId, score, comment)
                onReviewSubmitted() // Callback para indicar que se ha enviado la reseña
            }) {
                Text("Aceptar")
            }
        }
    }
}