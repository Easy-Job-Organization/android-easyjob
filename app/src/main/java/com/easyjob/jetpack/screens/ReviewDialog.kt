package com.easyjob.jetpack.screens

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.easyjob.jetpack.models.Professional
import com.easyjob.jetpack.viewmodels.ReviewViewModel

@Composable
fun ReviewDialog(
    professionalId: String,
    viewModel: ReviewViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
    onReviewSubmitted: () -> Unit
) {
    var score by remember { mutableDoubleStateOf(0.0) }
    var comment by remember { mutableStateOf("") }


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
                viewModel.submitReview(
                    professional = professionalId,
                    score = score,
                    comment = comment
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