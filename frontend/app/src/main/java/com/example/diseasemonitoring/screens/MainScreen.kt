package com.example.diseasemonitoring.screens

import AddDiseaseDialog
import BottomNavigationBar
import android.annotation.SuppressLint

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diseasemonitoring.viewmodels.DiseaseViewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diseasemonitoring.screens.views.NavigationItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiseaseScreen(viewModel: DiseaseViewModels = viewModel()) {

    val navController = rememberNavController()
    val diseaseList by viewModel.diseaseList.observeAsState(listOf())
    var showAddDiseaseDialog by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by remember { mutableStateOf(0) }

    val items = listOf(
        NavigationItem("Home", Icons.Default.Home, Icons.Default.Home),
        NavigationItem("Profile", Icons.Default.Person, Icons.Default.Person),
        NavigationItem("Doctor Appointments", Icons.Default.DateRange, Icons.Default.DateRange),
        NavigationItem("Settings", Icons.Default.Settings, Icons.Default.Settings),
        NavigationItem("Sign Up", Icons.Default.ArrowForward, Icons.Default.ArrowForward)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch { drawerState.close() }
                            when (item.title) {
                                "Home" -> navController.navigate("home")
                                "Profile" -> navController.navigate("profile")
                                "Doctor Appointments" -> navController.navigate("doctor_appointments")
                                "Sign Up" -> {
                                    // Handle Sign-Up navigation or functionality
                                }
                            }
                        },
                        icon = {
                            Icon(imageVector = item.selectedIcon, contentDescription = item.title)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Disease Monitoring") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { showAddDiseaseDialog = true },
                    icon = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Disease")
                    },
                    text = { Text("Add Disease") },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    HomeScreen(diseaseList, viewModel, showAddDiseaseDialog) { showAddDiseaseDialog = it }
                }
                composable("profile") { ProfileScreen() }
                composable("doctor_appointments") { DoctorAppointmentsScreen() }
            }

            if (showAddDiseaseDialog) {
                AddDiseaseDialog(
                    onDismiss = { showAddDiseaseDialog = false },
                    onAddDisease = { newDisease ->
                        viewModel.addDisease(newDisease)
                        showAddDiseaseDialog = false
                    }
                )
            }
        }
    }
}
