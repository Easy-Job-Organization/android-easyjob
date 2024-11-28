package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.sharp.CarRepair
import androidx.compose.material.icons.sharp.Carpenter
import androidx.compose.material.icons.sharp.CleaningServices
import androidx.compose.material.icons.sharp.Construction
import androidx.compose.material.icons.sharp.ElectricBolt
import androidx.compose.material.icons.sharp.Engineering
import androidx.compose.material.icons.sharp.Grass
import androidx.compose.material.icons.sharp.Hardware
import androidx.compose.material.icons.sharp.ImagesearchRoller
import androidx.compose.material.icons.sharp.LocalFireDepartment
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material.icons.sharp.Plumbing
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.easyjob.jetpack.R
import com.easyjob.jetpack.models.Service
import com.easyjob.jetpack.viewmodels.ProfessionalProfileViewModel

@Composable
fun CardSearch(
    id: String,
    image: String,
    descriptionImage: String,
    name: String = "Cargando",
    stars: Int = 0,
    comment: String = "0",
    professionalViewModel: ProfessionalProfileViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    cities: List<String> = emptyList()
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(10.dp, RoundedCornerShape(18.dp)) // Sombra aplicada al Box
            .background(Color.White, RoundedCornerShape(18.dp))
            .clickable {
                navController.navigate("professionalProfileClient/$id")
            }

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    0.5.dp,
                    Color.LightGray,
                    RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {

            AsyncImage(
                model = image,
                contentDescription = descriptionImage,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp),
                contentScale = ContentScale.Crop,
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box {
                        RatingStars(rating = stars.toDouble(), iconSize = 16)
                    }

                    Text(
                        fontWeight = FontWeight.Thin,
                        fontSize = 14.sp,
                        color = Color(0xFF133c55),
                        text = "($stars)",
                        modifier = Modifier.padding(start = 2.dp)
                    )

                    /*Text(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color(0xFF133c55),
                        text = "XX opiniones"
                    )*/

                }
                Text(
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    text = cities.joinToString(separator = ", "),
                )
            }

            Box(modifier = Modifier.weight(1f))

            Icon(
                Icons.Rounded.KeyboardArrowRight,
                contentDescription = "Ingresar"
            )


        }
    }

}

val iconMapSpecialities = mapOf(
    "Carpintero" to Icons.Sharp.Carpenter,
    "Plomero" to Icons.Sharp.Plumbing,
    "Electricista" to Icons.Sharp.ElectricBolt,
    "Limpiador" to Icons.Sharp.CleaningServices,
    "Albañil" to Icons.Sharp.Engineering,
    "Cerrajero" to Icons.Sharp.Lock,
    "Jardinero" to Icons.Sharp.Grass,
    "Mecánico" to Icons.Sharp.CarRepair,
    "Fontanero" to Icons.Sharp.Plumbing,
    "Pintor" to Icons.Sharp.ImagesearchRoller,
    "Soldador" to Icons.Sharp.LocalFireDepartment,
)

@Composable
fun FilterCard(
    descriptionIcon: String,
    iconSize: Int,
    text: String,
    color: Color,
    backgroundColor: Color = Color.Transparent,
    navController: NavController = rememberNavController(),
    click: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable {
                if (click) {
                    navController.navigate("results/${descriptionIcon}")
                }
            }
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
                    imageVector = iconMapSpecialities[text]?: Icons.Sharp.Construction,
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

@Composable
fun GroupChatCard(
    id: String,
    image: String,
    descriptionImage: String,
    name: String = "Cargando",
    score: Double = 0.0,
    navController: NavController = rememberNavController(),
    isClient: Boolean
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(10.dp, RoundedCornerShape(18.dp))
            .background(Color.White, RoundedCornerShape(18.dp))
            .clickable {
                navController.navigate("chat/$id")
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    0.5.dp,
                    Color.LightGray,
                    RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 15.dp, vertical = 16.dp),
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
                if(!isClient){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box {
                            RatingStars(rating = score, iconSize = 16)
                        }

                    Text(
                        fontWeight = FontWeight.Thin,
                        fontSize = 14.sp,
                        color = Color(0xFF133c55),
                        text = "(${ String.format("%.1f", score) })",
                        modifier = Modifier.padding(start = 2.dp)
                    )


                    }
                }
            }

        }

    }

}

@Composable
fun AppointmentCard(
    id: String,
    name: String,
    service: Service?,
    date: String,
    hour: String,
    photo_url: String,
    navController: NavController
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(10.dp, RoundedCornerShape(12.dp)) // Sombra aplicada al Box
            .background(Color.White, RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("appointment/$id")
            }

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {



            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.Start
            ) {

               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                   ,
                   horizontalArrangement = Arrangement.SpaceBetween
               ) {
                   Column(
                       modifier = Modifier.width(210.dp),
                       verticalArrangement = Arrangement.spacedBy((-5).dp)
                   ) {

                       Text(
                           fontWeight = FontWeight.Medium,
                           fontSize = 22.sp,
                           color = Color.Black,
                           text = name,
                           lineHeight = 30.sp
                       )

                       if (service != null) {
                           Text(
                               fontWeight = FontWeight.Normal,
                               fontSize = 12.sp,
                               color = Color.Gray,
                               text = service.title,
                               lineHeight = 30.sp
                           )
                       }
                   }
                   AsyncImage(
                       model = photo_url,
                       contentDescription = name,
                       modifier = Modifier
                           .clip(CircleShape)
                           .size(50.dp),
                       contentScale = ContentScale.Crop,
                       error = painterResource(R.drawable.ic_launcher_background)
                   )
               }

                Box(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                )

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy((-4).dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {

                        Icon(
                            imageVector = Icons.Rounded.Event,
                            contentDescription = "Calendar Icon",
                            tint = Color(0xFF636363),
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                        )

                        Text(
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFF636363),
                            text = date,
                            lineHeight = 30.sp
                        )

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = "Clock Icon",
                            tint = Color(0xFF636363),
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                        )

                        Text(
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color(0xFF636363),
                            text = hour,
                            lineHeight = 30.sp
                        )

                    }
                }

            }


            /*IconButton(
                onClick = {},
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(end = 6.dp)
            ) {

                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = "Calendar Icon",
                    tint = Color(0xFF9B9B9B),
                    modifier = Modifier
                        .size(42.dp)
                        .padding(end = 12.dp)
                )

            }*/

        }

    }
}
