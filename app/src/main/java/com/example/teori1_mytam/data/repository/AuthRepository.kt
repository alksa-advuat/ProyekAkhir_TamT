package com.example.teori1_mytam.data.repository

import com.example.teori1_mytam.data.api.ApiService
import com.example.teori1_mytam.data.model.request.RegisterRequest
import com.example.teori1_mytam.data.model.response.AuthResponse
import com.example.teori1_mytam.utils.Resource

class AuthRepository(
    private val authApi: ApiService
) {
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Resource<AuthResponse> {
        return try {
            val response = authApi.register(
                RegisterRequest(
                    name                  = name,
                    email                 = email,
                    password              = password,
                    password_confirmation = password
                )
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Registrasi gagal")
        }
    }

    suspend fun login(email: String, password: String): Resource<AuthResponse> {
        return try {
            val users = authApi.getAllUsers()
            val user  = users.find {
                it.email == email && it.password == password
            }
            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("Email atau password salah")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Login gagal")
        }
    }
}