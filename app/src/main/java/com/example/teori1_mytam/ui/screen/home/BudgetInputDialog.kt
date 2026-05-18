package com.example.teori1_mytam.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green = Color(0xFF6B9B6B)

@Composable
fun BudgetInputDialog(
    currentBudget: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var input by remember { mutableStateOf(currentBudget.toString()) }
    val isValid = input.toIntOrNull() != null && (input.toIntOrNull() ?: 0) > 0

    AlertDialog(
        onDismissRequest = onDismiss,
        shape            = RoundedCornerShape(16.dp),
        title            = { Text("Set Budget Harian", fontWeight = FontWeight.Bold) },
        text             = {
            Column {
                Text(
                    "Masukkan budget kamu hari ini:",
                    fontSize = 13.sp,
                    color    = Color.Gray
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value         = input,
                    onValueChange = { input = it.filter { c -> c.isDigit() } },
                    placeholder   = { Text("Contoh: 25000") },
                    leadingIcon   = {
                        Text(
                            "Rp",
                            modifier   = Modifier.padding(start = 12.dp),
                            fontWeight = FontWeight.Bold,
                            color      = Green
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine      = true,
                    modifier        = Modifier.fillMaxWidth(),
                    shape           = RoundedCornerShape(12.dp),
                    colors          = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Green,
                        unfocusedBorderColor = Color(0xFFE0E0E0)
                    )
                )
                if (!isValid && input.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Masukkan nominal yang valid",
                        fontSize = 11.sp,
                        color    = Color.Red
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick  = { if (isValid) onConfirm(input.toInt()) },
                enabled  = isValid,
                colors   = ButtonDefaults.buttonColors(containerColor = Green),
                shape    = RoundedCornerShape(8.dp)
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal", color = Color.Gray)
            }
        }
    )
}