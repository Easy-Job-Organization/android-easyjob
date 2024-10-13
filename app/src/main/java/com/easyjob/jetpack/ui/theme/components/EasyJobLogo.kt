package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easyjob.jetpack.R

@Composable
fun EasyJobLogo(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Centers horizontally
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.easyjob_logo_main_color),
            contentDescription = "Easyjob logo",
            modifier = Modifier
                .size(125.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Easy Job",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(59, 130, 246),
        )
        Text(
            text = "Profesionales a tu gusto",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
        )
    }
}