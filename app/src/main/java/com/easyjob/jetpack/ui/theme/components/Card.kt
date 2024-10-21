package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp) // Añade un padding externo
            .shadow(10.dp, RoundedCornerShape(18.dp)) // Sombra aplicada al Box
            .background(Color.White, RoundedCornerShape(18.dp))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(1.dp, Color.Gray, RoundedCornerShape(18.dp))
                .padding(horizontal = 15.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {

            AsyncImage(
                model = image,
                contentDescription = descriptionImage,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp),
                error = painterResource(R.drawable.ic_launcher_background)
            )

            Box(modifier = Modifier.width(7.dp))

            Column(
                modifier = Modifier
                    .width(220.dp)
                    .wrapContentHeight()
                    .padding(vertical = 10.dp, horizontal = 5.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    fontWeight = FontWeight.Medium,
                    fontSize = 17.sp,
                    color = Color(0xFF133c55),
                    text = name,
                    lineHeight = 30.sp
                )

                FilterCard(
                    icon = Icons.Sharp.Lock,
                    descriptionIcon = "Cerrajero",
                    iconSize = 14,
                    text = "Cerrajero",
                    color = Color(0xff133c55),
                    backgroundColor = Color(0x32133c55)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box {
                        RatingStars(rating = stars, iconSize = 16)
                    }

                    Text(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color(0xFF133c55),
                        text = "$comments opiniones"
                    )

                }
            }

            Box(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                navController.navigate("professionalProfileClient/$id")
            }) {
                Icon(
                    Icons.Rounded.KeyboardArrowRight,
                    contentDescription = "Ingresar"
                )
            }

        }
    }


}


@Composable
fun FilterCard(
    icon: ImageVector,
    descriptionIcon: String,
    iconSize: Int,
    text: String,
    color: Color,
    backgroundColor: Color = Color.Transparent,
    navController: NavController = rememberNavController()
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable { navController.navigate("results/${descriptionIcon}") }
    ) {

        Row(
            modifier = Modifier
                .background(backgroundColor)
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(modifier = Modifier) {
                Icon(
                    imageVector = icon,
                    contentDescription = descriptionIcon,
                    tint = color,
                    modifier = Modifier.size(iconSize.dp)
                )
            }

            Box(modifier = Modifier.width(10.dp))

            Text(
                fontWeight = FontWeight.Normal,
                fontSize = iconSize.sp,
                color = color,
                text = text
            )

        }

    }
}