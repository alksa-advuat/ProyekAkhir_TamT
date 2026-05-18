package com.example.teori1_mytam.ui.components

import com.example.teori1_mytam.data.model.Mangan

data class AppState(
    val selectedFood: Mangan?      = null,
    val activeTab: Int             = 0,
    val favoriteItems: Set<String> = emptySet(),
    val logMakanan: List<Mangan>   = emptyList(),
    val allMakanan: List<Mangan>   = emptyList(),
    val isLoading: Boolean         = true,
    val isError: Boolean           = false,
    val budgetAwal: Int            = 25000,
    val budget: Int                = 25000,
    val belanjaList: List<Mangan>  = emptyList()
)