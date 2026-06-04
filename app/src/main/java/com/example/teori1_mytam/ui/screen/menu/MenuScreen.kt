package com.example.teori1_mytam.ui.screen.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.teori1_mytam.data.model.Mangan
import com.example.teori1_mytam.ui.components.MenuGridCard

private val Green  = Color(0xFF6B9B6B)
private val BgGray = Color(0xFFFAFAFA)

enum class KategoriMenu { SEMUA, NASI, LAUK, MINUM, SNACK }

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    allMakanan: List<Mangan>,
    favoriteItems: Set<String>,
    onFavoriteToggle: (String) -> Unit,
    onFoodClick: (Mangan) -> Unit
) {
    var query    by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf(KategoriMenu.SEMUA) }

    val filtered = allMakanan.filter { food ->
        (query.isBlank() ||
                food.nama.contains(query, true) ||
                food.deskripsi.contains(query, true)) &&
                when (kategori) {
                    KategoriMenu.SEMUA -> true
                    KategoriMenu.NASI  -> food.nama.contains("Nasi", true)
                    KategoriMenu.LAUK  -> listOf("Ayam","Ikan","Tempe","Tahu","Gado")
                        .any { food.nama.contains(it, true) }
                    KategoriMenu.MINUM -> listOf("Es","Jus","Teh")
                        .any { food.nama.contains(it, true) }
                    KategoriMenu.SNACK -> food.kalori < 200
                }
    }

    Column(modifier = modifier.fillMaxSize().background(BgGray)) {
        Column(
            Modifier.fillMaxWidth().background(Color.White)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 16.dp)
        ) {
            Text("Menu Lengkap", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("${allMakanan.size} menu tersedia", fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(14.dp))

            OutlinedTextField(
                value         = query,
                onValueChange = { query = it },
                placeholder   = { Text("Cari menu...", fontSize = 14.sp) },
                leadingIcon   = { Icon(Icons.Default.Search, null, tint = Green) },
                trailingIcon  = {
                    if (query.isNotEmpty())
                        Icon(
                            Icons.Default.Close, null,
                            Modifier.clickable { query = "" },
                            tint = Color.Gray
                        )
                },
                modifier   = Modifier.fillMaxWidth().height(52.dp),
                shape      = RoundedCornerShape(12.dp),
                colors     = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Green,
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                singleLine = true
            )

            Spacer(Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(KategoriMenu.entries.toList()) { k ->
                    FilterChip(
                        selected = kategori == k,
                        onClick  = { kategori = k },
                        label    = {
                            Text(
                                when (k) {
                                    KategoriMenu.SEMUA -> "🍽 Semua"
                                    KategoriMenu.NASI  -> "🍚 Nasi"
                                    KategoriMenu.LAUK  -> "🍗 Lauk"
                                    KategoriMenu.MINUM -> "🥤 Minum"
                                    KategoriMenu.SNACK -> "🍿 Snack"
                                },
                                fontSize = 13.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Green,
                            selectedLabelColor     = Color.White
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled             = true,
                            selected            = kategori == k,
                            borderColor         = Color.Transparent,
                            selectedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }

        if (filtered.isEmpty()) {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("😅", fontSize = 48.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Menu tidak ditemukan", fontWeight = FontWeight.Bold)
                    Text("Coba kata kunci lain", fontSize = 13.sp, color = Color.Gray)
                }
            }
        } else {
            Text(
                "${filtered.size} menu ditemukan",
                fontSize = 12.sp,
                color    = Color.Gray,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)
            )
            LazyVerticalGrid(
                columns               = GridCells.Fixed(2),
                contentPadding        = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement   = Arrangement.spacedBy(12.dp)
            ) {
                items(filtered) { food ->
                    MenuGridCard(
                        food, favoriteItems.contains(food.nama),
                        onFavoriteToggle, onFoodClick
                    )
                }
            }
        }
    }
}