package com.easyjob.jetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyjob.jetpack.ui.theme.EasyjobTheme
import com.easyjob.jetpack.ui.theme.components.ActionCard
import com.easyjob.jetpack.ui.theme.components.ButtonIconLink
import com.easyjob.jetpack.ui.theme.components.ButtonSection
import com.easyjob.jetpack.ui.theme.components.DropdownMenu1
import com.easyjob.jetpack.ui.theme.components.InformationCard
import com.easyjob.jetpack.ui.theme.components.Input
import com.easyjob.jetpack.ui.theme.components.PrimaryButton
import com.easyjob.jetpack.ui.theme.components.ProfileSection
import com.easyjob.jetpack.ui.theme.components.SecondaryButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyjobTheme {
                Components()
            }
        }
    }
}

@Composable
fun Components() {

    var example by remember {
        mutableStateOf("")
    }

    val options = listOf("Opción 1", "Opción 2", "Opción 3")
    var selectedOption by remember { mutableStateOf("Selecciona una opción") }

    var activeSection by remember {
        mutableStateOf(true)
    }

    val profesiones = listOf(
        Service(
            title = "Electricista",
            descriptions = listOf("Montaje de una red electrica en un apartamento pequeno"),
            prices = listOf(45900.0)
        ),
        Service(
            title = "Plomeria",
            descriptions = listOf("Asesoria en fugas de agua de viviendas", "Asesoria en fugas de agua de viviendas"),
            prices = listOf(70000.0, 45000.0)
        )
    )

    Scaffold (modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            /*PrimaryButton(text = "prueba", width = 250, onClick = {})
            Box(modifier = Modifier.weight(1f))
            Input(value = example, label = "Nombre", onValueChange = { example = it }, width = 250)
            Box(modifier = Modifier.weight(1f))
            DropdownMenu1(options = options, selectedOption = selectedOption, onOptionSelected = { option -> selectedOption = option })
            Box(modifier = Modifier.weight(1f))
            SecondaryButton(text = "prueba2", onClick = {}, width = 250)
            Box(modifier = Modifier.weight(1f))*/
            ProfileSection(
                image = "https://e7.pngegg.com/pngimages/324/645/png-clipart-pokeball-pokeball-thumbnail.png",
                descriptionImage = "mclovin",
                name = "Sebastián Escobar Marín",
                cityCountry = "Cali, Colombia",
                stars = 4,
                comments = "444"
            )
            Box(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonSection(active = activeSection, text = "Información", onClick = { activeSection = !activeSection }, width = 185)
                ButtonSection(active = !activeSection, text = "Información", onClick = { activeSection = !activeSection }, width = 185)
            }
            Box(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                if (activeSection) {
                    InformationCard(services = profesiones)
                }
            }
            /*Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ActionCard(image = R.drawable.ic_launcher_background, descriptionImage = "example", title = "Ayuda", onClick = { /*TODO*/ })
                ActionCard(image = R.drawable.ic_launcher_foreground, descriptionImage = "example", title = "Easy job +", onClick = { /*TODO*/ })
                ActionCard(image = R.drawable.ic_launcher_foreground, descriptionImage = "example", title = "Historial", onClick = { /*TODO*/ })
            }
            Box(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
                ButtonIconLink(icon = Icons.Default.LocationOn, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Mis direcciones", color = Color.Black)
                ButtonIconLink(icon = Icons.Rounded.ShoppingCart, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Mis medios de pago", color = Color.Black)
                ButtonIconLink(icon = Icons.Default.Settings, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Configuración", color = Color.Black)
                ButtonIconLink(icon = Icons.Outlined.Person, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Administrar cuenta", color = Color.Black)
                ButtonIconLink(icon = Icons.Default.LocationOn, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Legal", color = Color.Black)
                ButtonIconLink(icon = Icons.Default.ExitToApp, descriptionIcon = "Home", onClick = { /*TODO*/ }, text = "Cerrar sesión", color = Color.Red)
            }*/
        }
    }
}

class Service(
    title: String,
    descriptions: List<String>,
    prices: List<Double>
)


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EasyjobTheme {
        Components()
    }
}