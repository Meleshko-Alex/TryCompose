package com.meleha.trycompose.ui.navigationBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun NavigationScreen() {
    var currentTab by remember {
        mutableStateOf(Tab.Items)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = currentTab.label) },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )

        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) {
            TabScreen(tab = currentTab)
        }

        NavigationBar {
            Tab.values().forEach { tab ->
                NavigationBarItem(
                    selected = tab == currentTab,
                    onClick = { currentTab = tab },
                    label = { Text(text = tab.label) },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ItemsScreen() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Item Screen", fontSize = 28.sp)
    }
}

@Composable
fun SettingsScreen() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Settings Screen", fontSize = 28.sp)
    }
}

@Composable
fun ProfileScreen() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Profile Screen", fontSize = 28.sp)
    }
}

@Composable
fun TabScreen(tab: Tab) {
    when(tab) {
        Tab.Items -> ItemsScreen()
        Tab.Settings -> SettingsScreen()
        Tab.Profile -> ProfileScreen()
    }
}