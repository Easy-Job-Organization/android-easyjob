package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easyjob.jetpack.Service

@Composable
fun InformationCard(services: List<Service>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 6.dp)
    ) {
        services.forEach { service ->

            Text(
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = Color(0xFF133c55),
                text = "",
                lineHeight = 30.sp
            )

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF133c55),
                    text = "",
                    lineHeight = 30.sp
                )

                Text(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF133c55),
                    text = "$",
                    lineHeight = 30.sp
                )

            }
        }
    }
}