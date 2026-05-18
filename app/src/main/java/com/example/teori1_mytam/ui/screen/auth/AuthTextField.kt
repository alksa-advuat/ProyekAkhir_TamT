package com.example.teori1_mytam.ui.screen.auth

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

private val Green = Color(0xFF6B9B6B)

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value            = value,
        onValueChange    = onValueChange,
        label            = { Text(label) },
        leadingIcon      = leadingIcon,
        trailingIcon     = trailingIcon,
        modifier         = Modifier.then(Modifier),
        shape            = RoundedCornerShape(12.dp),
        singleLine       = true,
        visualTransformation = if (isPassword)
            PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions  = KeyboardOptions(keyboardType = keyboardType),
        colors           = OutlinedTextFieldDefaults.colors(
            focusedBorderColor   = Green,
            unfocusedBorderColor = Color(0xFFE0E0E0),
            focusedLabelColor    = Green
        )
    )
}