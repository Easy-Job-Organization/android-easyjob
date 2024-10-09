package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R

@Composable
fun CardSearch(
    id: String,
    image: String,
    descriptionImage: String,
    name: String = "Cargando",
    stars: Int = 0,
    comments: String = "0",
    navController: NavController = rememberNavController()
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray)
            .padding(horizontal = 15.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {

        AsyncImage(
            model = image,
            contentDescription = descriptionImage,
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp),
            error = painterResource(R.drawable.ic_launcher_background)
        )

        Box(modifier = Modifier.width(7.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 10.dp, horizontal = 5.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color(0xFF133c55),
                text = name,
                lineHeight = 30.sp
            )

            ButtonIconLink(
                icon = Icons.Sharp.Lock,
                descriptionIcon = "Cerrajero",
                onClick = { /*TODO*/ },
                text = "Cerrajero",
                backgroundColor = Color(0xcc133c55),
                color = Color(0xff133c55),
                enable = false,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(modifier = Modifier) {
                    RatingStars(rating = stars)
                }

                Text(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color(0xFF133c55),
                    text = "$comments opiniones"
                )

            }
        }

        Box(modifier = Modifier.weight(1f))

        IconButton(onClick = {
            navController.navigate("professionalProfile?id=$id")
        }) {
            Icon(
                Icons.Rounded.KeyboardArrowRight,
                contentDescription = "Ingresar"
            )
        }

    }
}