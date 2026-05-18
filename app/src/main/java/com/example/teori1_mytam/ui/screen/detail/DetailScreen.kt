package com.example.teori1_mytam.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.teori1_mytam.data.model.Mangan
import com.example.teori1_mytam.ui.components.NutriBadge
import kotlinx.coroutines.launch

private val Green      = Color(0xFF6B9B6B)
private val GreenLight = Color(0xFFEDF4ED)

@Composable
fun DetailScreen(
    food: Mangan,
    isFavorite: Boolean,
    isLogged: Boolean,
    isBeli: Boolean,
    budgetCukup: Boolean,
    onFavToggle: (String) -> Unit,
    onLogToggle: (Mangan) -> Unit,
    onBeliToggle: (Mangan) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope             = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize().background(Color.White)) {
        Column(Modifier.fillMaxSize().padding(24.dp)) {
            Box(Modifier.fillMaxWidth().height(250.dp)) {
                AsyncImage(
                    model              = food.imageUrl,
                    contentDescription = food.nama,
                    modifier           = Modifier.fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd).padding(12.dp)
                        .size(36.dp).clip(CircleShape)
                        .background(Color.White.copy(.65f))
                        .clickable { onFavToggle(food.nama) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isFavorite) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        null,
                        tint     = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
            Text(food.nama, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(food.deskripsi, fontSize = 15.sp, color = Color.DarkGray)
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(Modifier.height(16.dp))
            Text(
                "Harga: Rp ${food.harga}",
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                color      = Green
            )
            Spacer(Modifier.height(12.dp))

            Card(
                Modifier.fillMaxWidth(),
                shape  = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(GreenLight)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(16.dp),
                    Arrangement.SpaceEvenly
                ) {
                    NutriBadge("🔥 Kalori",  "${food.kalori}",  "kcal")
                    NutriBadge("🥩 Protein", "${food.protein}", "gram")
                    NutriBadge("🍚 Karbo",   "${food.karbo}",   "gram")
                    NutriBadge("🧈 Lemak",   "${food.lemak}",   "gram")
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    onBeliToggle(food)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            if (!isBeli)
                                "✅ ${food.nama} dibeli! Budget berkurang Rp ${food.harga}"
                            else "↩️ Pembelian ${food.nama} dibatalkan"
                        )
                    }
                },
                enabled  = isBeli || budgetCukup,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = if (isBeli) Color(0xFFFFB74D)
                    else Color(0xFF42A5F5),
                    disabledContainerColor = Color(0xFFBDBDBD)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    if (isBeli) Icons.Default.Close else Icons.Default.ShoppingCart,
                    null, modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    when {
                        isBeli       -> "Batalkan Pembelian (Rp ${food.harga})"
                        !budgetCukup -> "Budget Tidak Cukup"
                        else         -> "Beli - Rp ${food.harga}"
                    },
                    fontSize = 15.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    onLogToggle(food)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            if (!isLogged)
                                "${food.nama} dicatat ke tracker nutrisi! 🥗"
                            else "${food.nama} dihapus dari tracker"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor = if (isLogged) Color(0xFFE57373) else Green
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    if (isLogged) Icons.Default.Close else Icons.Default.AddCircle,
                    null, modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    if (isLogged) "Hapus dari Catatan" else "Catat Makanan Ini",
                    fontSize = 16.sp
                )
            }

            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick  = onBackClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors   = ButtonDefaults.outlinedButtonColors(contentColor = Green),
                shape    = RoundedCornerShape(12.dp)
            ) { Text("Kembali", fontSize = 16.sp) }
        }
        SnackbarHost(
            snackbarHostState,
            Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
        )
    }
}