package com.example.teori1_mytam.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.teori1_mytam.data.repository.ManganRepository
import com.example.teori1_mytam.ui.components.AppBottomBar
import com.example.teori1_mytam.ui.components.AppState
import com.example.teori1_mytam.ui.components.ErrorScreen
import com.example.teori1_mytam.ui.components.LoadingScreen
import com.example.teori1_mytam.ui.components.ProfilePlaceholder
import com.example.teori1_mytam.ui.screen.detail.DetailScreen
import com.example.teori1_mytam.ui.screen.home.HomeScreen
import com.example.teori1_mytam.ui.screen.menu.MenuScreen
import com.example.teori1_mytam.ui.screen.nutrisi.NutrisiScreen
import com.example.teori1_mytam.utils.SessionManager
import com.example.teori1_mytam.ui.theme.Teori1_MyTAMTheme

class MainActivity : ComponentActivity() {

    private lateinit var sessionManager: SessionManager
    private var currentBudget = 25000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sessionManager      = SessionManager(this)
        val userName        = sessionManager.getName()   ?: ""
        val userEmail       = sessionManager.getEmail()  ?: ""
        val savedBudgetAwal = sessionManager.getBudget()
        currentBudget       = savedBudgetAwal

        val today    = java.text.SimpleDateFormat(
            "yyyy-MM-dd", java.util.Locale.getDefault()
        ).format(java.util.Date())
        val lastDate = sessionManager.getBudgetDate()

        val savedSisa = if (today == lastDate) {
            val sisa = sessionManager.getSisaBudget()
            if (sisa == -1) savedBudgetAwal else sisa
        } else {
            sessionManager.saveBudgetDate(today)
            sessionManager.saveSisaBudget(savedBudgetAwal)
            savedBudgetAwal
        }

        setContent {
            Teori1_MyTAMTheme {
                var state by remember {
                    mutableStateOf(
                        AppState(
                            budgetAwal = savedBudgetAwal,
                            budget     = savedSisa
                        )
                    )
                }
                val repository = remember { ManganRepository() }

                // Simpan budget setiap kali berubah
                LaunchedEffect(state.budget) {
                    currentBudget = state.budget
                    sessionManager.saveSisaBudget(state.budget)
                }

                LaunchedEffect(Unit) {
                    state = state.copy(isLoading = true, isError = false)
                    try {
                        val data = repository.getMakanan()
                        state = if (data.isEmpty()) {
                            state.copy(isLoading = false, isError = true)
                        } else {
                            state.copy(isLoading = false, allMakanan = data)
                        }
                    } catch (_: Exception) {
                        state = state.copy(isLoading = false, isError = true)
                    }
                }

                val toggleBeli: (com.example.teori1_mytam.data.model.Mangan) -> Unit = { food ->
                    val sudahBeli = state.belanjaList.contains(food)
                    val newState  = if (sudahBeli) {
                        state.copy(
                            belanjaList = state.belanjaList - food,
                            budget      = state.budget + food.harga
                        )
                    } else {
                        if (state.budget >= food.harga) {
                            state.copy(
                                belanjaList = state.belanjaList + food,
                                budget      = state.budget - food.harga
                            )
                        } else state
                    }
                    state = newState
                }

                val onBudgetChange: (Int) -> Unit = { newBudget ->
                    sessionManager.saveBudget(newBudget)
                    sessionManager.saveSisaBudget(newBudget)
                    sessionManager.saveBudgetDate(today)
                    state = state.copy(
                        budgetAwal  = newBudget,
                        budget      = newBudget,
                        belanjaList = emptyList()
                    )
                }

                val toggleFav: (String) -> Unit = { name ->
                    state = state.copy(
                        favoriteItems = if (state.favoriteItems.contains(name))
                            state.favoriteItems - name
                        else state.favoriteItems + name
                    )
                }

                val toggleLog: (com.example.teori1_mytam.data.model.Mangan) -> Unit = { food ->
                    state = state.copy(
                        logMakanan = if (state.logMakanan.contains(food))
                            state.logMakanan - food
                        else state.logMakanan + food
                    )
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    val bottomPad = if (state.selectedFood == null) 80.dp else 0.dp

                    when {
                        state.isLoading -> LoadingScreen()
                        state.isError   -> ErrorScreen()
                        else -> when {
                            state.selectedFood != null -> DetailScreen(
                                food         = state.selectedFood!!,
                                isFavorite   = state.favoriteItems
                                    .contains(state.selectedFood!!.nama),
                                isLogged     = state.logMakanan
                                    .contains(state.selectedFood!!),
                                isBeli       = state.belanjaList
                                    .contains(state.selectedFood!!),
                                budgetCukup  = state.budget >= state.selectedFood!!.harga,
                                onFavToggle  = toggleFav,
                                onLogToggle  = toggleLog,
                                onBeliToggle = toggleBeli,
                                onBackClick  = { state = state.copy(selectedFood = null) },
                                modifier     = Modifier.fillMaxSize()
                            )
                            state.activeTab == 0 -> HomeScreen(
                                modifier         = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = bottomPad),
                                allMakanan       = state.allMakanan,
                                favoriteItems    = state.favoriteItems,
                                budget           = state.budget,
                                budgetAwal       = state.budgetAwal,
                                belanjaList      = state.belanjaList,
                                onFavoriteToggle = toggleFav,
                                onFoodClick      = { state = state.copy(selectedFood = it) },
                                onBudgetChange   = onBudgetChange,
                                onProfileClick   = { state = state.copy(activeTab = 3) }
                            )
                            state.activeTab == 1 -> MenuScreen(
                                modifier         = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = bottomPad),
                                allMakanan       = state.allMakanan,
                                favoriteItems    = state.favoriteItems,
                                onFavoriteToggle = toggleFav,
                                onFoodClick      = { state = state.copy(selectedFood = it) }
                            )
                            state.activeTab == 2 -> NutrisiScreen(
                                modifier    = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = bottomPad),
                                logMakanan  = state.logMakanan,
                                allMakanan  = state.allMakanan,
                                onLogToggle = toggleLog,
                                onFoodClick = { state = state.copy(selectedFood = it) }
                            )
                            else -> ProfilePlaceholder(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = bottomPad),
                                nama     = userName,
                                email    = userEmail
                            )
                        }
                    }

                    if (state.selectedFood == null) {
                        AppBottomBar(
                            activeTab   = state.activeTab,
                            onTabChange = { state = state.copy(activeTab = it) },
                            modifier    = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        sessionManager.saveSisaBudget(currentBudget)
    }
}