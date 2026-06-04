package com.example.teori1_mytam.data.model.response

data class AuthResponse(
    val id: String?       = null,
    val name: String?     = null,
    val email: String?    = null,
    val password: String? = null,
    val token: String?    = null,
    val message: String?  = null,
    val budget: Int?      = null,
    val sisaBudget: Int?  = null,
    val budgetDate: String? = null,
    val user: UserData?   = null
)

data class UserData(
    val id: Int    = 0,
    val name: String  = "",
    val email: String = ""
)