package com.example.teori1_mytam.ui.screen.home

import com.example.teori1_mytam.ui.screen.home.BudgetInputDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teori1_mytam.data.model.Mangan
import com.example.teori1_mytam.ui.components.FavListCard
import com.example.teori1_mytam.ui.components.RekomCard
import com.example.teori1_mytam.ui.components.SectionHeader
import androidx.compose.foundation.clickable

private val Green      = Color(0xFF6B9B6B)
private val GreenLight = Color(0xFFEDF4ED)
private val GreenSoft  = Color(0xFFE8F5E9)
private val BgGray     = Color(0xFFFAFAFA)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    allMakanan: List<Mangan>,
    favoriteItems: Set<String>,
    budget: Int,
    budgetAwal: Int,
    belanjaList: List<Mangan>,
    onFavoriteToggle: (String) -> Unit,
    onFoodClick: (Mangan) -> Unit,
    onBudgetChange: (Int) -> Unit,
    onProfileClick: () -> Unit
) {
    var showBudgetDialog by remember { mutableStateOf(false) }
    val totalBelanja = belanjaList.sumOf { it.harga }
    val progress     = if (budgetAwal > 0)
        (budget / budgetAwal.toFloat()).coerceIn(0f, 1f) else 0f

    if (showBudgetDialog) {
        BudgetInputDialog(
            currentBudget = budgetAwal,
            onConfirm     = { newBudget ->
                onBudgetChange(newBudget)
                showBudgetDialog = false
            },
            onDismiss = { showBudgetDialog = false }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgGray)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text("MealKost Kuu..", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Pastikan Kalorimu Terpenuhi! Sesuai Budgetmu",
                    fontSize = 11.sp, color = Color.DarkGray
                )
            }
            Icon(
                Icons.Rounded.AccountCircle, null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick() },  // ← tambah ini
                tint = Color(0xFFA5C1A5)
            )
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            shape    = RoundedCornerShape(16.dp),
            colors   = CardDefaults.cardColors(containerColor = GreenLight)
        ) {
            Column(Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccountBox, null, tint = Green)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Budget Hari Ini",
                        fontWeight = FontWeight.SemiBold,
                        color      = Color.DarkGray
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick  = { showBudgetDialog = true },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Budget",
                            tint               = Green,
                            modifier           = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text("Rp $budgetAwal", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                Box(
                    Modifier.fillMaxWidth().height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFD0E8D0))
                ) {
                    Box(
                        Modifier.fillMaxWidth(progress).fillMaxHeight()
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                when {
                                    progress > 0.5f -> Green
                                    progress > 0.2f -> Color(0xFFFFB74D)
                                    else            -> Color(0xFFE57373)
                                }
                            )
                    )
                }

                Spacer(Modifier.height(6.dp))
                Text("Terpakai: Rp $totalBelanja", fontSize = 11.sp, color = Color.Gray)
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = Color.LightGray)
                Spacer(Modifier.height(16.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Column {
                        Text("Sisa Budget", color = Color.DarkGray, fontSize = 13.sp)
                        Text(
                            "Rp $budget",
                            fontSize   = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color      = when {
                                budget <= 0    -> Color(0xFFE57373)
                                budget <= 5000 -> Color(0xFFFFB74D)
                                else           -> Green
                            }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (budget > 5000) GreenSoft else Color(0xFFFFEBEE)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            when {
                                budget <= 0     -> "Habis 😵"
                                budget <= 5000  -> "Tipis ⚠️"
                                budget <= 15000 -> "Cukup 👍"
                                else            -> "Aman 🎉"
                            },
                            fontSize   = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color      = if (budget > 5000) Green else Color(0xFFE57373)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        SectionHeader("Rekomendasi Untukmu")
        Spacer(Modifier.height(12.dp))
        LazyRow(
            contentPadding        = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(allMakanan) { food ->
                RekomCard(food, favoriteItems.contains(food.nama), onFavoriteToggle, onFoodClick)
            }
        }

        Spacer(Modifier.height(24.dp))
        SectionHeader("Menu Favorit Anak Kost")
        Spacer(Modifier.height(12.dp))
        Column(
            Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            allMakanan.forEach { food ->
                FavListCard(food, favoriteItems.contains(food.nama), onFavoriteToggle, onFoodClick)
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}