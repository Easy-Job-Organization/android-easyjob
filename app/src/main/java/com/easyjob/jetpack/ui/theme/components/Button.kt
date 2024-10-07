package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// PRIMARY BUTTON
@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, width: Int, height: Int = 56) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff4091b6),
            contentColor = Color.White
        ),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp, // Elevación normal
            pressedElevation = 8.dp // Elevación cuando se presiona
        )
    ) {
        Text(text = text, color = Color.White, fontSize = 18.sp)
    }
}

// SECONDARY BUTTON
@Composable
fun SecondaryButton(text: String, onClick: () -> Unit, width: Int, height: Int = 56) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF838383)),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp, // Elevación normal
            pressedElevation = 8.dp // Elevación cuando se presiona
        )
    ) {
        Text(text = text, color = Color.Black, fontSize = 18.sp)
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit, width: Int, height: Int = 20) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color(0xFF838383)),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .background(color = Color.Transparent),
    ) {
        Text(text = text, color = Color(0xFF838383), fontSize = 16.sp)
    }
}


// BUTTON SECTION
@Composable
fun ButtonSection(active: Boolean, text: String, onClick: () -> Unit, width: Int, height: Int = 56) {

    val color = if (active) Color(0xFFd2963d) else Color(0xFF636363).copy(alpha = 0.7f)

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .border(1.dp, color, shape = getBottomLineShape(lineThicknessDp = 1.dp)),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(text = text, color = color , fontSize = 18.sp)
    }
}

@Composable
private fun getBottomLineShape(lineThicknessDp: Dp) : Shape {
    val lineThicknessPx = with(LocalDensity.current) {lineThicknessDp.toPx()}
    return GenericShape { size, _ ->
        // 1) Bottom-left corner
        moveTo(0f, size.height)
        // 2) Bottom-right corner
        lineTo(size.width, size.height)
        // 3) Top-right corner
        lineTo(size.width, size.height - lineThicknessPx)
        // 4) Top-left corner
        lineTo(0f, size.height - lineThicknessPx)
    }
}