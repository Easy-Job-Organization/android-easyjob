package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.easyjob.jetpack.viewmodels.ProfessionalClientViewModel

@Composable
fun LikeDialog(
    professionalId: String,
    professionalProfileViewModel: ProfessionalClientViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
    onLikeSubmitted: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(
                text = "Like al profesional",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¿Estás seguro de que deseas guardar este profesional?",
                    fontSize = 16.sp
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                professionalProfileViewModel.likeProfessional(professionalId)
                onLikeSubmitted()
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismissRequest()
            }) {
                Text("Cancelar")
            }
        }
    )
}