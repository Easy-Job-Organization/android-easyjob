package com.easyjob.jetpack.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyjob.jetpack.viewmodels.ReviewViewModel

@Composable
fun ReviewDialog(
    professionalId: String,
    initialScore: Double = 0.0,
    initialComment: String = "",
    reviewId: String? = null, // Nuevo parámetro para manejar actualización
    viewModel: ReviewViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
    onReviewSubmitted: () -> Unit
) {
    var score by remember { mutableDoubleStateOf(initialScore) }
    var comment by remember { mutableStateOf(initialComment) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = "Califica a este profesional",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Estrellas de rating
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
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
            }
        },
        confirmButton = {
            Button(onClick = {
                if (score == 0.0) {
                    Toast.makeText(
                        context,
                        "Por favor selecciona una calificación.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                if (comment.isBlank()) {
                    Toast.makeText(
                        context,
                        "Por favor escribe un comentario.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                // Llamar al ViewModel para enviar o actualizar la reseña
                viewModel.submitReview(
                    professional = professionalId,
                    score = score,
                    comment = comment,
                    reviewId = reviewId // Pasar el ID si se trata de una actualización
                )
                onReviewSubmitted()
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("Cancelar")
            }
        }
    )
}
