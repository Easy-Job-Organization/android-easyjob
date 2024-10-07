package com.easyjob.jetpack.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easyjob.jetpack.R
import com.easyjob.jetpack.ui.theme.components.ActionCard
import com.easyjob.jetpack.ui.theme.components.ButtonIconLink
import com.easyjob.jetpack.ui.theme.components.ProfileSection
import com.easyjob.jetpack.ui.theme.components.Topbar

@Composable
fun ProfileScreen() {



    Scaffold(
        topBar = {
            Topbar(
                title = "Mi perfil",
                icon = Icons.Default.Edit,
                onEditClick = {}
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
    ) { innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(15.dp)
        ) {

            ProfileSection(
                image = "https://e7.pngegg.com/pngimages/324/645/png-clipart-pokeball-pokeball-thumbnail.png",
                descriptionImage = "mclovin",
                name = "Sebastián Escobar Marín",
                cityCountry = "Cali, Colombia",
                stars = 4,
                comments = "444"
            )
            
            Box(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionCard(image = R.drawable.ic_launcher_background, descriptionImage = "example", title = "Ayuda", onClick = { /*TODO*/ })
                ActionCard(image = R.drawable.ic_launcher_foreground, descriptionImage = "example", title = "Easy job +", onClick = { /*TODO*/ })
                ActionCard(image = R.drawable.ic_launcher_foreground, descriptionImage = "example", title = "Historial", onClick = { /*TODO*/ })
            }

            Box(modifier = Modifier.height(15.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonIconLink(icon = Icons.Default.LocationOn, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Mis direcciones", color = Color.Black)
                ButtonIconLink(icon = Icons.Rounded.ShoppingCart, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Mis medios de pago", color = Color.Black)
                ButtonIconLink(icon = Icons.Default.Settings, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Configuración", color = Color.Black)
                ButtonIconLink(icon = Icons.Outlined.Person, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Administrar cuenta", color = Color.Black)
                ButtonIconLink(icon = Icons.Default.LocationOn, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Legal", color = Color.Black)
                ButtonIconLink(icon = Icons.Default.ExitToApp, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Cerrar sesión", color = Color.Red)
            }

        }

    }

}