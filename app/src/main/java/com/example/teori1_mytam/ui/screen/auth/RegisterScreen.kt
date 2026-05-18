package com.example.teori1_mytam.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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

private val Green      = Color(0xFF6B9B6B)
private val BgGray     = Color(0xFFFAFAFA)

@Composable
fun RegisterScreen(
    uiState: AuthUiState,
    onRegister: (name: String, email: String, password: String) -> Unit,
    onGoToLogin: () -> Unit
) {
    var nama        by remember { mutableStateOf("") }
    var email       by remember { mutableStateOf("") }
    var password    by remember { mutableStateOf("") }
    var konfirm     by remember { mutableStateOf("") }
    var showPass    by remember { mutableStateOf(false) }
    var showKonfirm by remember { mutableStateOf(false) }
    var localError  by remember { mutableStateOf("") }

    val isLoading = uiState is AuthUiState.Loading

    fun validate(): Boolean {
        return when {
            nama.isBlank()       -> { localError = "Nama tidak boleh kosong"; false }
            email.isBlank()      -> { localError = "Email tidak boleh kosong"; false }
            !email.contains("@") -> { localError = "Format email tidak valid"; false }
            password.length < 6  -> { localError = "Password minimal 6 karakter"; false }
            password != konfirm  -> { localError = "Konfirmasi password tidak cocok"; false }
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(64.dp))
            Text("🍱", fontSize = 64.sp)
            Spacer(Modifier.height(8.dp))
            Text("MealKost", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Green)
            Text("Daftar akun baru", fontSize = 14.sp, color = Color.Gray)
            Spacer(Modifier.height(36.dp))

            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(24.dp)) {
                    Text("Buat Akun", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Isi data diri kamu", fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.height(20.dp))

                    AuthTextField(
                        value         = nama,
                        onValueChange = { nama = it },
                        label         = "Nama Lengkap",
                        leadingIcon   = { Icon(Icons.Default.Person, null, tint = Green) }
                    )
                    Spacer(Modifier.height(14.dp))

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
                    Spacer(Modifier.height(14.dp))

                    AuthTextField(
                        value         = konfirm,
                        onValueChange = { konfirm = it },
                        label         = "Konfirmasi Password",
                        leadingIcon   = { Icon(Icons.Default.Lock, null, tint = Green) },
                        trailingIcon  = {
                            Icon(
                                if (showKonfirm) Icons.Default.VisibilityOff
                                else Icons.Default.Visibility,
                                null, tint = Color.Gray,
                                modifier = Modifier.clickable { showKonfirm = !showKonfirm }
                            )
                        },
                        isPassword = !showKonfirm
                    )

                    // Error dari lokal validasi
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
                        onClick  = { if (validate()) onRegister(nama, email, password) },
                        enabled  = !isLoading,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors   = ButtonDefaults.buttonColors(containerColor = Green),
                        shape    = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color  = Color.White,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Daftar Sekarang",
                                fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                Text("Sudah punya akun? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    "Masuk",
                    color    = Green,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onGoToLogin() }
                )
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}