package com.example.teori1_mytam.ui.screen.nutrisi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.teori1_mytam.data.model.Mangan
import com.example.teori1_mytam.ui.components.MacroBar
import com.example.teori1_mytam.ui.components.MiniInfo

private val Green      = Color(0xFF6B9B6B)
private val GreenLight = Color(0xFFEDF4ED)
private val BgGray     = Color(0xFFFAFAFA)

@Composable
fun NutrisiScreen(
    modifier: Modifier = Modifier,
    logMakanan: List<Mangan>,
    allMakanan: List<Mangan>,
    onLogToggle: (Mangan) -> Unit,
    onFoodClick: (Mangan) -> Unit
) {
    val tKal = 2000; val tProt = 60; val tKarbo = 250; val tLemak = 65
    val totKal   = logMakanan.sumOf { it.kalori }.coerceAtMost(tKal)
    val totProt  = logMakanan.sumOf { it.protein }.coerceAtMost(tProt)
    val totKarbo = logMakanan.sumOf { it.karbo }.coerceAtMost(tKarbo)
    val totLemak = logMakanan.sumOf { it.lemak }.coerceAtMost(tLemak)
    val kalProg  = (totKal / tKal.toFloat()).coerceIn(0f, 1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BgGray)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier.fillMaxWidth().background(Color.White)
                .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
        ) {
            Text("Nutrisi Harian", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Catat makananmu, pantau gizimu", fontSize = 12.sp, color = Color.Gray)
        }

        Spacer(Modifier.height(16.dp))

        Card(
            Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            shape     = RoundedCornerShape(20.dp),
            colors    = CardDefaults.cardColors(containerColor = Green),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Kalori Hari Ini", fontSize = 14.sp, color = Color.White.copy(.85f))
                Spacer(Modifier.height(16.dp))
                Box(Modifier.size(140.dp), Alignment.Center) {
                    Canvas(Modifier.size(140.dp)) {
                        drawArc(
                            Color.White.copy(.25f), -90f, 360f, false,
                            style = Stroke(14.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            Color.White, -90f, 360f * kalProg, false,
                            style = Stroke(14.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "$totKal",
                            fontSize   = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color      = Color.White
                        )
                        Text("/ $tKal kcal", fontSize = 11.sp, color = Color.White.copy(.75f))
                    }
                }
                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                    MiniInfo("Tercatat", "$totKal kcal")
                    Box(Modifier.width(1.dp).height(36.dp).background(Color.White.copy(.3f)))
                    MiniInfo("Sisa", "${(tKal - totKal).coerceAtLeast(0)} kcal")
                    Box(Modifier.width(1.dp).height(36.dp).background(Color.White.copy(.3f)))
                    MiniInfo(
                        "Status", when {
                            kalProg < 0.4f -> "Kurang 😟"
                            kalProg < 0.8f -> "Oke 👍"
                            kalProg < 1f   -> "Hampir ✅"
                            else           -> "Cukup 🎉"
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Card(
            Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            shape     = RoundedCornerShape(20.dp),
            colors    = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Makro Nutrisi", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text("Target harian mahasiswa aktif", fontSize = 11.sp, color = Color.Gray)
                Spacer(Modifier.height(16.dp))
                MacroBar("🥩 Protein",     totProt,  tProt,  "g", Color(0xFFE57373))
                Spacer(Modifier.height(14.dp))
                MacroBar("🍚 Karbohidrat", totKarbo, tKarbo, "g", Color(0xFFFFB74D))
                Spacer(Modifier.height(14.dp))
                MacroBar("🧈 Lemak",       totLemak, tLemak, "g", Color(0xFF64B5F6))
            }
        }

        Spacer(Modifier.height(20.dp))

        Column(Modifier.padding(horizontal = 24.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Column {
                    Text("Makanan Hari Ini", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(
                        if (logMakanan.isEmpty()) "Belum ada catatan"
                        else "${logMakanan.size} menu dicatat",
                        fontSize = 12.sp, color = Color.Gray
                    )
                }
                if (logMakanan.isNotEmpty()) {
                    TextButton(onClick = { logMakanan.toList().forEach { onLogToggle(it) } }) {
                        Text("Reset", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))

            if (logMakanan.isEmpty()) {
                Card(
                    Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(
                        Modifier.fillMaxWidth().padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🍽️", fontSize = 36.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Belum ada makanan dicatat",
                            fontWeight = FontWeight.Bold,
                            color      = Color.DarkGray
                        )
                        Text(
                            "Buka detail menu → tap \"Catat Makanan Ini\"",
                            fontSize = 12.sp, color = Color.Gray
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    logMakanan.forEach { food ->
                        Card(
                            Modifier.fillMaxWidth().clickable { onFoodClick(food) },
                            shape     = RoundedCornerShape(12.dp),
                            colors    = CardDefaults.cardColors(Color.White),
                            elevation = CardDefaults.cardElevation(1.dp)
                        ) {
                            Row(
                                Modifier.padding(12.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model              = food.imageUrl,
                                    contentDescription = food.nama,
                                    modifier           = Modifier.size(44.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(food.nama, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(
                                        "${food.kalori} kcal | P:${food.protein}g K:${food.karbo}g L:${food.lemak}g",
                                        fontSize = 11.sp, color = Color.Gray
                                    )
                                }
                                IconButton(
                                    onClick  = { onLogToggle(food) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Close, null,
                                        tint     = Color.LightGray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Column(Modifier.padding(horizontal = 24.dp)) {
            Text("Tambah dari Menu", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(
                "Ketuk + untuk mencatat apa yang kamu makan",
                fontSize = 12.sp, color = Color.Gray
            )
            Spacer(Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                allMakanan.forEach { food ->
                    val logged = logMakanan.contains(food)
                    Card(
                        Modifier.fillMaxWidth().clickable { onFoodClick(food) },
                        shape     = RoundedCornerShape(12.dp),
                        colors    = CardDefaults.cardColors(
                            if (logged) GreenLight else Color.White
                        ),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Row(
                            Modifier.padding(12.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model              = food.imageUrl,
                                contentDescription = food.nama,
                                modifier           = Modifier.size(44.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(food.nama, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(
                                    "${food.kalori} kcal | Rp ${food.harga}",
                                    fontSize = 11.sp, color = Color.Gray
                                )
                            }
                            IconButton(onClick = { onLogToggle(food) }) {
                                Icon(
                                    if (logged) Icons.Default.CheckCircle
                                    else Icons.Default.AddCircle,
                                    null,
                                    tint = if (logged) Green else Color.LightGray
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Column(Modifier.padding(horizontal = 24.dp)) {
            Text("Tips Nutrisi Anak Kost", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Spacer(Modifier.height(12.dp))
            listOf(
                Triple("💧", "Minum Air Cukup",
                    "Minimal 8 gelas/hari untuk metabolisme optimal."),
                Triple("🥚", "Protein Murah Meriah",
                    "Telur & tempe = sumber protein terbaik untuk budget kost."),
                Triple("⏰", "Jangan Skip Sarapan",
                    "Sarapan meningkatkan konsentrasi kuliah hingga 30%."),
                Triple("🌙", "Hindari Makan >9PM",
                    "Metabolisme melambat malam hari.")
            ).forEach { (e, t, d) ->
                Card(
                    Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    shape     = RoundedCornerShape(14.dp),
                    colors    = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                        Text(e, fontSize = 24.sp)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(t, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(d, fontSize = 12.sp, color = Color.Gray, lineHeight = 17.sp)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}