package com.example.teori1_mytam.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import com.example.teori1_mytam.data.model.Mangan

private val Green      = Color(0xFF6B9B6B)
private val GreenLight = Color(0xFFEDF4ED)

private val BgGray     = Color(0xFFFAFAFA)

// ─── Section Header ───────────────────────────────────────────────────────────
@Composable
fun SectionHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Icon(Icons.Default.MoreVert, null, tint = Color.Gray)
    }
}

// ─── Loading Screen ───────────────────────────────────────────────────────────
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Green)
            Spacer(Modifier.height(12.dp))
            Text("Memuat menu...", color = Color.Gray, fontSize = 14.sp)
        }
    }
}

// ─── Error Screen ─────────────────────────────────────────────────────────────
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().padding(32.dp), Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("😵", fontSize = 48.sp)
            Spacer(Modifier.height(12.dp))
            Text(
                "Gagal Memuat Data",
                fontWeight = FontWeight.Bold,
                fontSize   = 18.sp,
                color      = Color(0xFFE57373)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Pastikan koneksi internet kamu menyala",
                fontSize = 13.sp,
                color    = Color.Gray
            )
        }
    }
}

// ─── Profile Placeholder ──────────────────────────────────────────────────────
@Composable
fun ProfilePlaceholder(
    modifier: Modifier = Modifier,
    nama: String = "",
    email: String = ""
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgGray)
    ) {
        // Header hijau
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Green)
                .padding(top = 48.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Avatar inisial
                Box(
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (nama.isNotEmpty())
                            nama.first().uppercaseChar().toString() else "?",
                        fontSize   = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    nama.ifEmpty { "Guest" },
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    email.ifEmpty { "-" },
                    fontSize = 13.sp,
                    color    = Color.White.copy(.8f)
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Info cards
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Informasi Akun",
                fontWeight = FontWeight.Bold,
                fontSize   = 15.sp,
                modifier   = Modifier.padding(bottom = 4.dp)
            )

            ProfileInfoCard("📧", "Email", email.ifEmpty { "-" })
            ProfileInfoCard("👤", "Nama Lengkap", nama.ifEmpty { "-" })
            ProfileInfoCard("🔐", "Status Akun", "Aktif ✓")
        }

        Spacer(Modifier.height(32.dp))

        // Tombol logout
        Column(Modifier.padding(horizontal = 24.dp)) {
            Text(
                "Lainnya",
                fontWeight = FontWeight.Bold,
                fontSize   = 15.sp,
                modifier   = Modifier.padding(bottom = 12.dp)
            )

            Card(
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(12.dp),
                colors    = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val sessionManager = com.example.teori1_mytam.utils.SessionManager(context)

                            // Simpan dulu semua data budget sebelum clear
                            val savedBudgetAwal = sessionManager.getBudget()
                            val savedSisa       = sessionManager.getSisaBudget()
                            val savedDate       = sessionManager.getBudgetDate()

                            // Hapus session (login data)
                            sessionManager.clearSession()

                            // Kembalikan data budget yang sudah disimpan
                            sessionManager.saveBudget(savedBudgetAwal)
                            sessionManager.saveSisaBudget(savedSisa)
                            sessionManager.saveBudgetDate(savedDate)

                            // Pindah ke AuthActivity
                            val intent = android.content.Intent(
                                context,
                                com.example.teori1_mytam.ui.screen.auth.AuthActivity::class.java
                            ).apply {
                                flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                                        android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            context.startActivity(intent)
                        }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🚪", fontSize = 20.sp)
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "Keluar",
                        fontSize  = 14.sp,
                        color     = Color(0xFFE57373),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileInfoCard(icon: String, label: String, value: String) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 20.sp)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(label, fontSize = 11.sp, color = Color.Gray)
                Text(value, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

// ─── Rekomenasi Card ──────────────────────────────────────────────────────────
@Composable
fun RekomCard(
    food: Mangan,
    isFav: Boolean,
    onFavToggle: (String) -> Unit,
    onClick: (Mangan) -> Unit
) {
    Card(
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier  = Modifier.width(130.dp).clickable { onClick(food) }
    ) {
        Column(Modifier.padding(12.dp)) {
            Box(Modifier.fillMaxWidth().height(90.dp)) {
                AsyncImage(
                    model            = food.imageUrl,
                    contentDescription = food.nama,
                    modifier         = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                    contentScale     = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd).padding(4.dp)
                        .size(24.dp).clip(CircleShape)
                        .background(Color.White.copy(.7f))
                        .clickable { onFavToggle(food.nama) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        null,
                        tint     = if (isFav) Color.Red else Color.Gray,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                food.nama,
                fontWeight = FontWeight.Bold,
                fontSize   = 13.sp,
                maxLines   = 1,
                overflow   = TextOverflow.Ellipsis
            )
            Text("Rp ${food.harga}", color = Color.LightGray, fontSize = 12.sp)
            Row(Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, null, Modifier.size(11.dp), tint = Green)
                Spacer(Modifier.width(3.dp))
                Text("${food.kalori} kcal", color = Color.Gray, fontSize = 11.sp)
            }
        }
    }
}

// ─── Fav List Card ────────────────────────────────────────────────────────────
@Composable
fun FavListCard(
    food: Mangan,
    isFav: Boolean,
    onFavToggle: (String) -> Unit,
    onClick: (Mangan) -> Unit
) {
    Card(
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier  = Modifier.fillMaxWidth().clickable { onClick(food) }
    ) {
        Row(
            Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model              = food.imageUrl,
                contentDescription = food.nama,
                modifier           = Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)),
                contentScale       = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(food.nama, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(
                    food.deskripsi,
                    color    = Color.Gray,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text("Rp ${food.harga}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(Modifier.width(8.dp))
            Icon(
                if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                null,
                tint     = if (isFav) Color.Red else Color.LightGray,
                modifier = Modifier.size(20.dp).clickable { onFavToggle(food.nama) }
            )
        }
    }
}

// ─── Menu Grid Card ───────────────────────────────────────────────────────────
@Composable
fun MenuGridCard(
    food: Mangan,
    isFav: Boolean,
    onFavToggle: (String) -> Unit,
    onClick: (Mangan) -> Unit
) {
    Card(
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier  = Modifier.fillMaxWidth().clickable { onClick(food) }
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(120.dp)) {
                AsyncImage(
                    model              = food.imageUrl,
                    contentDescription = food.nama,
                    modifier           = Modifier.fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd).padding(8.dp)
                        .size(28.dp).clip(CircleShape)
                        .background(Color.White.copy(.75f))
                        .clickable { onFavToggle(food.nama) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        null,
                        tint     = if (isFav) Color.Red else Color.Gray,
                        modifier = Modifier.size(15.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart).padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(.55f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("${food.kalori} kcal", fontSize = 10.sp, color = Color.White)
                }
            }
            Column(Modifier.padding(10.dp)) {
                Text(
                    food.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 13.sp,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                Text(
                    food.deskripsi,
                    fontSize = 11.sp,
                    color    = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Text(
                        "Rp ${food.harga}",
                        fontWeight = FontWeight.Bold,
                        fontSize   = 13.sp,
                        color      = Green
                    )
                    Box(
                        Modifier.clip(RoundedCornerShape(4.dp))
                            .background(GreenLight)
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text(
                            "P:${food.protein}g",
                            fontSize   = 10.sp,
                            color      = Green,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// ─── Nutri Badge ──────────────────────────────────────────────────────────────
@Composable
fun NutriBadge(label: String, value: String, unit: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 11.sp, color = Color.Gray)
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(unit,  fontSize = 10.sp, color = Color.Gray)
    }
}

// ─── Macro Bar ────────────────────────────────────────────────────────────────
@Composable
fun MacroBar(label: String, current: Int, target: Int, unit: String, color: Color) {
    val progress = (current / target.toFloat()).coerceIn(0f, 1f)
    Column {
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            Text(label, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text("$current / $target $unit", fontSize = 12.sp, color = Color.Gray)
        }
        Spacer(Modifier.height(6.dp))
        Box(
            Modifier.fillMaxWidth().height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF0F0F0))
        ) {
            Box(
                Modifier.fillMaxWidth(progress).fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )
        }
    }
}

// ─── Mini Info ────────────────────────────────────────────────────────────────
@Composable
fun MiniInfo(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(label, fontSize = 11.sp, color = Color.White.copy(.75f))
    }
}