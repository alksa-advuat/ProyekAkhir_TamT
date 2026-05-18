package com.example.teori1_mytam.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green  = Color(0xFF6B9B6B)
private val BgGray = Color(0xFFFAFAFA)

@Composable
fun LoginScreen(
    uiState: AuthUiState,
    onLogin: (email: String, password: String) -> Unit,
    onGoToRegister: () -> Unit
) {
    var email      by remember { mutableStateOf("") }
    var password   by remember { mutableStateOf("") }
    var showPass   by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf("") }

    val isLoading = uiState is AuthUiState.Loading

    fun validate(): Boolean {
        return when {
            email.isBlank()      -> { localError = "Email tidak boleh kosong"; false }
            !email.contains("@") -> { localError = "Format email tidak valid"; false }
            password.isBlank()   -> { localError = "Password tidak boleh kosong"; false }
            else                 -> { localError = ""; true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFFE8F5E9), BgGray, Color.White))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🍱", fontSize = 64.sp)
            Spacer(Modifier.height(8.dp))
            Text("MealKost", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Green)
            Text("Selamat datang kembali!", fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.height(36.dp))

            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(24.dp)) {
                    Text("Masuk", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Login ke akun MealKost kamu",
                        fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.height(20.dp))

                    AuthTextField(
                        value         = email,
                        onValueChange = { email = it },
                        label         = "Email",
                        leadingIcon   = { Icon(Icons.Default.Email, null, tint = Green) },
                        keyboardType  = KeyboardType.Email
                    )
                    Spacer(Modifier.height(14.dp))

                    AuthTextField(
                        value         = password,
                        onValueChange = { password = it },
                        label         = "Password",
                        leadingIcon   = { Icon(Icons.Default.Lock, null, tint = Green) },
                        trailingIcon  = {
                            Icon(
                                if (showPass) Icons.Default.VisibilityOff
                                else Icons.Default.Visibility,
                                null, tint = Color.Gray,
                                modifier = Modifier.clickable { showPass = !showPass }
                            )
                        },
                        isPassword = !showPass
                    )

                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Lupa password?",
                        color    = Green,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.End).clickable { }
                    )

                    // Error lokal
                    if (localError.isNotEmpty()) {
                        Spacer(Modifier.height(10.dp))
                        Text("⚠ $localError",
                            color = Color(0xFFE57373), fontSize = 12.sp)
                    }

                    // Error dari API
                    if (uiState is AuthUiState.Error) {
                        Spacer(Modifier.height(10.dp))
                        Text("⚠ ${uiState.message}",
                            color = Color(0xFFE57373), fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick  = { if (validate()) onLogin(email, password) },
                        enabled  = !isLoading,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors   = ButtonDefaults.buttonColors(containerColor = Green),
                        shape    = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color       = Color.White,
                                modifier    = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Masuk",
                                fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                Text("Belum punya akun? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    "Daftar",
                    color      = Green,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier   = Modifier.clickable { onGoToRegister() }
                )
            }
        }
    }
}