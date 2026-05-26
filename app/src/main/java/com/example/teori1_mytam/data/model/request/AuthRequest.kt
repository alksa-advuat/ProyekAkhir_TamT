package com.example.teori1_mytam.data.model.request

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val budget: Int = 25000,        // ← tambah
    val sisaBudget: Int = 25000,    // ← tambah
    val budgetDate: String = ""     // ← tambah
)

// ← tambah ini untuk update budget
data class UpdateBudgetRequest(
    val budget: Int,
    val sisaBudget: Int,
    val budgetDate: String
)