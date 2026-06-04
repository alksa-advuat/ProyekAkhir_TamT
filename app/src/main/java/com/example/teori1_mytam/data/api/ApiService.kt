package com.example.teori1_mytam.data.api

import com.example.teori1_mytam.data.model.Mangan
import com.example.teori1_mytam.data.model.request.LoginRequest
import com.example.teori1_mytam.data.model.request.RegisterRequest
import com.example.teori1_mytam.data.model.response.AuthResponse
import com.example.teori1_mytam.data.model.request.UpdateBudgetRequest
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // ── Gist (data makanan) ──
    @GET("menu_makanan.json")
    suspend fun getMakanan(): List<Mangan>

    // ── MockAPI (auth) ──
    @POST("user")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @GET("user")
    suspend fun getAllUsers(): List<AuthResponse>

    // ← update budget user berdasarkan ID
    @PUT("user/{id}")
    suspend fun updateBudget(
        @Path("id") userId: String,
        @Body request: UpdateBudgetRequest
    ): AuthResponse
}