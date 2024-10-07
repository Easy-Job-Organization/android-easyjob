package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
        services.forEach { service ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp, vertical = 4.dp)
            ) {

                Text(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    color = Color(0xFF133c55),
                    text = service.title,
                    lineHeight = 26.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                for (i in service.descriptions.indices) {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {

                        Text(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color(0xff444444),
                            text = service.descriptions[i],
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(bottom = 0.dp)
                        )

                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xff444444),
                            text = "$${service.prices[i]}",
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                    }

                }

            }
        }
}