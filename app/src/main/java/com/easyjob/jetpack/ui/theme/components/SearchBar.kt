package com.easyjob.jetpack.ui.theme.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchBar(
    hint: String,
    prevText: String = "",
    navController: NavController = rememberNavController(),
    width: Int? = null,
    modifier: Modifier = Modifier // Accept a Modifier as a parameter
) {
    var searchQuery by remember { mutableStateOf(prevText) }

    Box(
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(50)) // Apply external modifier here
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            shape = RoundedCornerShape(50), // Rounded borders
            singleLine = true,
            modifier = Modifier
                .then(
                    if (width != null) {
                        Modifier.width(width.dp)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            },
            placeholder = {
                Text(text = hint, fontSize = 16.sp)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(20, 152, 213),
                unfocusedIndicatorColor = Color(20, 152, 213),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search // Sets the action to "Search"
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    navController.navigate("results/${searchQuery}")
                }
            )
        )
    }
}