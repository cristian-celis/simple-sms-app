package com.celisdev.simplesmsapp.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp

@Composable
fun CustomOutlinedTextField(value: String, label: String, keyboardOptions: KeyboardOptions, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(label, style = TextStyle(fontSize = 16.sp)) },
        textStyle = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.W500),
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions
    )
}