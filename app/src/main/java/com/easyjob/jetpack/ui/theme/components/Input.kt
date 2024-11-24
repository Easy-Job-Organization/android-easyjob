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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
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
            .background(Color.White)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
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
            .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .background(Color.Transparent)
    ) {
        TextField(
            value = selectedOption,
            onValueChange = onOptionSelected,
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
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
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    TextField(
        value = description,
        onValueChange = onDescriptionChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp) // Altura para que funcione como área de texto
            .shadow(8.dp, RoundedCornerShape(8.dp)) // Sombra con radio de 8dp
            .padding(8.dp),
        placeholder = { Text("Escribe la descripción aquí...") },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White
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

    // DatePicker Dialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, pickedYear, pickedMonth, pickedDay ->
            onDateSelected("$pickedDay/${pickedMonth + 1}/$pickedYear")
        },
        year, month, day
    )

    // TimePicker Dialog
    val timePickerDialog = TimePickerDialog(
        context,
        { _, pickedHour, pickedMinute ->
            onTimeSelected("$pickedHour:$pickedMinute")
        },
        hour, minute, true
    )

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Fecha seleccionada: $selectedDate")
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(text = "Elegir fecha", onClick = { datePickerDialog.show() })

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Hora seleccionada: $selectedTime")
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(text = "Elegir hora", onClick = { timePickerDialog.show() })
    }
}

