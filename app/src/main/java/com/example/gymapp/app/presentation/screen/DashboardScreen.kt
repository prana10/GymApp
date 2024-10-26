package com.example.gymapp.app.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.app.data.repository.UserRepositoryImpl
import com.example.gymapp.app.domain.usecase.LogoutUseCase
import com.example.gymapp.app.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: MainViewModel) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val showLogoutDialog by viewModel.showLogoutDialog.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(WindowInsets.statusBars.asPaddingValues())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Header Drawer
                    Text(
                        text = "Welcome Admin",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    DrawerMenuItem(icon = Icons.Default.Home, label = "Home") {
                    }
                    DrawerMenuItem(icon = Icons.Default.Add, label = "Add Member") {
                        navController.navigate("add_new_member")
                    }
                    DrawerMenuItem(icon = Icons.Default.ShoppingCart, label = "Fees Pending") {
                    }
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    DrawerMenuItem(icon = Icons.Default.Edit, label = "Add/Update Fee") {
                    }
                    DrawerMenuItem(
                        icon = Icons.Default.Settings,
                        label = "Change Password/Mobile"
                    ) {
                    }
                    DrawerMenuItem(icon = Icons.Default.ExitToApp, label = "Log Out") {
                        viewModel.showLogoutDialog() // Tampilkan dialog logout
                    }
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Dashboard") },
                        navigationIcon = {
                            IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        },
                        actions = {
                            IconButton(onClick = { viewModel.showLogoutDialog() }) {
                                Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("add_new_member")
                        },
                        containerColor = Color.Green
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Member")
                    }
                },
                content = { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        TabRow(selectedTabIndex = selectedTabIndex) {
                            Tab(
                                selected = selectedTabIndex == 0,
                                onClick = { selectedTabIndex = 0 },
                                text = { Text("Active Members") },
                                unselectedContentColor = Color.Gray
                            )
                            Tab(
                                selected = selectedTabIndex == 1,
                                onClick = { selectedTabIndex = 1 },
                                text = { Text("Inactive Members") },
                                unselectedContentColor = Color.Gray
                            )
                        }

                        when (selectedTabIndex) {
                            0 -> Text("Active Members Content", modifier = Modifier.padding(16.dp))
                            1 -> Text(
                                "Inactive Members Content",
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        if (showLogoutDialog) {
                            LogoutDialog(
                                onDismiss = { viewModel.hideLogoutDialog() },
                                onConfirm = {
                                    viewModel.logout()
                                    navController.navigate("login") {
                                        popUpTo("dashboard") { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                }
            )
        }
    )
}


@Composable
fun DrawerMenuItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = label, modifier = Modifier.padding(end = 16.dp))
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    val viewModel =
        MainViewModel(logoutUseCase = LogoutUseCase(userRepository = UserRepositoryImpl()))
    DashboardScreen(navController = navController, viewModel = viewModel)
}