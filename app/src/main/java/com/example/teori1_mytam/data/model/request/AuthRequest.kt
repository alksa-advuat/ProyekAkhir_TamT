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
    val budget: Int = 15000,
    val sisaBudget: Int = 15000,
    val budgetDate: String = ""
)

// ← tambah ini untuk update budget
data class UpdateBudgetRequest(
    val budget: Int,
    val sisaBudget: Int,
    val budgetDate: String
)