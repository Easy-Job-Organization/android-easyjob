package com.easyjob.jetpack.ui.theme.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.view.Display.Mode
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun Input(
    value: String,
    label: String = "Ingresa aquí",
    onValueChange: (String) -> Unit,
    width: Int? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        value = value,
        placeholder = { Text(modifier = Modifier.background(Color.Transparent), text = label) },
        shape = RoundedCornerShape(4.dp),
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (visualTransformation is PasswordVisualTransformation) KeyboardType.Password else KeyboardType.Text,
            imeAction = ImeAction.Done
        )
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu1(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    placeholder: String = "Selecciona una opción",
    width: Int? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier
            .wrapContentHeight()
            .then(
                if (width != null) {
                    Modifier
                        .width(width.dp)
                } else {
                    Modifier
                        .wrapContentWidth()
                }
            )
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            .background(Color.White),

    ) {
        TextField(
            value = if (selectedOption.isEmpty()) placeholder else selectedOption,
            onValueChange = onOptionSelected,
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun DescriptionTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp) // Altura para que funcione como área de texto
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
            .padding(8.dp),
        placeholder = { Text(label) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp), // Forma con bordes redondeados
        maxLines = 5 // Limitar el número de líneas si lo prefieres
    )
}


@Composable
fun DateTimePicker(
    selectedDate: String,
    selectedTime: String,
    onDateSelected: (String) -> Unit,
    onTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // Configuración para bloquear días pasados
    val datePickerDialog = DatePickerDialog(
        context,
        { _, pickedYear, pickedMonth, pickedDay ->
            onDateSelected("$pickedDay/${pickedMonth + 1}/$pickedYear")
        },
        year, month, day
    ).apply {
        datePicker.minDate = calendar.timeInMillis // Bloquear días pasados
    }

    // Configuración para bloquear horas pasadas
    val timePickerDialog = TimePickerDialog(
        context,
        { _, pickedHour, pickedMinute ->
            onTimeSelected("$pickedHour:$pickedMinute")
        },
        hour, minute, true
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fecha seleccionada:"
            )
            Text(
                text = "$selectedDate",
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .padding(8.dp)
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(6.dp))
                    ,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF133c55),
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 4.dp, // Elevación normal
                        pressedElevation = 8.dp // Elevación cuando se presiona
                    ),
                    shape = RoundedCornerShape(6.dp),
                    onClick = { datePickerDialog.show()  }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Event,
                        contentDescription = "Fecha seleccionada",
                        tint = Color.Black,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }


        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hora seleccionada:"
            )
            Text(
                text = "$selectedTime",
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

                Button(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .padding(8.dp)
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(6.dp))
                    ,

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF133c55),
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 4.dp, // Elevación normal
                        pressedElevation = 8.dp // Elevación cuando se presiona
                    ),
                    shape = RoundedCornerShape(6.dp),
                    onClick = {
                        timePickerDialog.show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.HourglassBottom,
                        contentDescription = "Fecha seleccionada",
                        tint = Color.Black,
                        modifier = Modifier.size(44.dp)
                    )
                }
            }
        }
    }
}

