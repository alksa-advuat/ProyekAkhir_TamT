package com.example.teori1_mytam.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val Green     = Color(0xFF6B9B6B)
private val GreenSoft = Color(0xFFE8F5E9)

@Composable
fun AppBottomBar(
    activeTab: Int,
    onTabChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier       = modifier,
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        listOf(
            Triple(Icons.Default.Home,           "Home",    0),
            Triple(Icons.Default.Menu,           "Menu",    1),
            Triple(Icons.Default.FavoriteBorder, "Nutrisi", 2),
            Triple(Icons.Default.Person,         "Profile", 3)
        ).forEach { (icon, label, idx) ->
            NavigationBarItem(
                icon     = { Icon(icon, null) },
                label    = { Text(label) },
                selected = activeTab == idx,
                onClick  = { onTabChange(idx) },
                colors   = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green,
                    selectedTextColor = Green,
                    indicatorColor    = GreenSoft
                )
            )
        }
    }
}