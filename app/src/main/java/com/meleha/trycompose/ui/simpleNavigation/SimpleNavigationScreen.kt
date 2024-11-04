package com.meleha.trycompose.ui.simpleNavigation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meleha.trycompose.R

sealed class Route(@StringRes val titleRes: Int = 0) {
    object AddItem : Route(R.string.add_item)

    sealed class Tab(
        @StringRes titleRes: Int,
        val icon: ImageVector
    ) : Route(titleRes) {
        object Items : Tab(R.string.items, Icons.Default.List)
        object Settings : Tab(R.string.settings, Icons.Default.Settings)
        object Profile : Tab(R.string.items, Icons.Default.AccountBox)
    }
}

val RootTabs = listOf(Route.Tab.Items, Route.Tab.Settings, Route.Tab.Profile)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleNavigationScreen(itemsRepository: ItemsRepository = ItemsRepository.get()) {
    val items by itemsRepository.getItems().collectAsStateWithLifecycle()
    val stack = remember {
        mutableStateListOf<Route>(Route.Tab.Items)
    }
    val currentRoute = stack.last()
    val isRoot = stack.size == 1

    BackHandler(enabled = !isRoot) {
        stack.removeLast()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = currentRoute.titleRes),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { if (!isRoot) stack.removeLast() }) {
                        Icon(
                            imageVector = if (isRoot)
                                Icons.Default.Menu
                            else
                                Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.main_menu)
                        )
                    }
                },
                actions = {
                    var showPopupMenu by remember { mutableStateOf(false) }
                    val context = LocalContext.current
                    IconButton(onClick = { showPopupMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more_actions)
                        )
                    }
                    DropdownMenu(
                        expanded = showPopupMenu,
                        onDismissRequest = { showPopupMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.about)) },
                            onClick = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.about_text), Toast.LENGTH_SHORT
                                ).show()
                                showPopupMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.clear)) },
                            onClick = {
                                itemsRepository.clear()
                                showPopupMenu = false
                            }
                        )
                    }
                })
        },
        floatingActionButton = {
            if (currentRoute == Route.Tab.Items) {
                FloatingActionButton(onClick = { stack.add(Route.AddItem) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_new_item)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            if (isRoot) {
                NavigationBar {
                    RootTabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute == tab,
                            label = { Text(text = stringResource(id = tab.titleRes)) },
                            onClick = {
                                stack.clear()
                                stack.add(tab)
                            }, icon = { 
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = stringResource(id = tab.titleRes)
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentRoute) {
                Route.Tab.Items -> ItemsScreen(items)
                Route.Tab.Profile -> SettingsScreen()
                Route.Tab.Settings -> ProfileScreen()
                Route.AddItem -> {
                    AddItemScreen(
                        onSubmitNewItem = {
                            itemsRepository.addItem(it)
                            stack.removeLast()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(onSubmitNewItem: (String) -> Unit) {
    var newItemValue by remember { mutableStateOf("") }
    val isAddEnabled by remember {
        derivedStateOf { newItemValue.isNotEmpty() }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = newItemValue,
            label = { Text(stringResource(R.string.enter_a_new_value)) },
            singleLine = true,
            onValueChange = { newValue ->
                newItemValue = newValue
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = isAddEnabled,
            onClick = { onSubmitNewItem(newItemValue) }
        ) {
            Text(text = stringResource(id = R.string.add_new_item))
        }
    }
}

@Composable
fun ItemsScreen(items: List<String>) {
    if (items.isEmpty()) {
        Text(
            text = stringResource(R.string.no_items),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
        )
    } else {
        LazyColumn {
            items(items) { item ->
                Text (
                    text = item,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    Text(
        text = "Settings Screen",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
    )
}

@Composable
fun ProfileScreen() {
    Text(
        text = "Profile Screen",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
    )
}