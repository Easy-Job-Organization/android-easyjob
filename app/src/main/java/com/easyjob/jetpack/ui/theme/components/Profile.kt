package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.easyjob.jetpack.R


@Composable
fun ProfileSection(
    image: String?,
    descriptionImage: String,
    name: String = "Cargando",
    cityCountry: String = "Cargando",
    iconSize: Int,
    stars: Int,
    comments: String = "0")
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AsyncImage(
            model = image,
            contentDescription = descriptionImage,
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp),
            error = painterResource(R.drawable.ic_launcher_background)
        )

        Box(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF133c55),
                text = name,
                lineHeight = 30.sp
            )

            Text(
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                color = Color(0xFF133c55),
                text = cityCountry
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                
                Box(modifier = Modifier) {
                    if (stars > -1) RatingStars(rating = stars, iconSize = iconSize)
                }
                
                Text(
                    fontWeight = FontWeight.Normal,
                    fontSize = iconSize.sp,
                    color = Color(0xFF133c55),
                    text = "$comments opiniones"
                )
                
            }
        }
    }
}

@Composable
fun ButtonIconLink(
    icon: ImageVector,
    descriptionIcon: String,
    onClick: () -> Unit,
    text: String,
    color: Color,
    backgroundColor: Color = Color.Transparent,
    enable: Boolean = true
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(0.dp),
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF133c55),
        ),
        shape = RoundedCornerShape(0.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 3.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(modifier = Modifier) {
                Icon(
                    imageVector = icon,
                    contentDescription = descriptionIcon,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Box(modifier = Modifier.width(20.dp))

            Text(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = color,
                text = text
            )

        }
    }
}


@Composable
fun ActionCard(image: Int = R.drawable.ic_launcher_background, descriptionImage: String, title: String = "hola", onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .width(120.dp)
            .height(110.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(6.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF133c55),
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 4.dp, // Elevación normal
            pressedElevation = 8.dp // Elevación cuando se presiona
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.size(50.dp)) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = descriptionImage
                )
            }

            Text(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF133c55),
                text = title
            )

        }
    }
}

@Composable
fun RatingStars(rating: Int, iconSize: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val totalStars = 5
        for (i in 1..totalStars) {
            val starColor = if (i <= rating) Color(0xFFd2963d) else Color(0x3Cd2963d).copy(alpha = 0.5f)

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(iconSize.dp)
            )
        }
    }
}