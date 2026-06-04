package com.example.teori1_mytam.ui.screen.auth

sealed class AuthUiState {
    object Idle    : AuthUiState()
    object Loading : AuthUiState()
    data class Success(
        val token: String,
        val name: String,
        val email: String,
        val userId: String   // ← tambah
    ) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}