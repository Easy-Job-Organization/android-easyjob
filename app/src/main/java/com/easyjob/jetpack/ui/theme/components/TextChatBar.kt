package com.easyjob.jetpack.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun TextChatBar(
    hint: String,
    prevText: String = "",
    width: Int? = null
) {
    var textQuery by remember { mutableStateOf(prevText) }

    Box(
        modifier = Modifier
            .shadow(4.dp, RoundedCornerShape(50))
            .background(Color(0xFFF5F5F5), RoundedCornerShape(50))
            .border(0.5.dp, Color(0xFF666666), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .then(
                    if (width != null) {
                        Modifier.width(width.dp)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                )
        ) {
            Icon(
                imageVector = Icons.Outlined.EmojiEmotions, // Cambiar a ícono de emoji
                contentDescription = "Emoji",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = textQuery,
                onValueChange = { textQuery = it },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                decorationBox = { innerTextField ->
                    if (textQuery.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.AttachFile, // Cambiar a ícono de clip
                contentDescription = "Adjuntar",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun RecordChat() {

    Box(
        modifier = Modifier
            .size(62.dp)
            .padding(8.dp)
            .background(Color(0xFF4091B6), RoundedCornerShape(100)),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.Mic, // Cambiar a ícono de clip
            contentDescription = "Record",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

    }

}