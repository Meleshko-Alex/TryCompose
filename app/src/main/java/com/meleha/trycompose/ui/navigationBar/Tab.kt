package com.meleha.trycompose.ui.navigationBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tab(
    val label: String,
    val icon: ImageVector
) {
    Items("Items", Icons.Default.List),
    Settings("Settings", Icons.Default.Settings),
    Profile("Profile", Icons.Default.AccountBox)
}