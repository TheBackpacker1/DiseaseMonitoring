package com.example.diseasemonitoring.screens

import AddDiseaseDialog
import BottomNavigationBar
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diseasemonitoring.viewmodels.DiseaseViewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.diseasemonitoring.models.Disease
import com.example.diseasemonitoring.models.PrescriptionTimes
import com.example.diseasemonitoring.screens.views.DiseaseMedicationCard
import com.example.diseasemonitoring.screens.views.NavigationItem
import kotlinx.coroutines.launch
import java.util.Calendar

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
                    NavigationDrawerItem(label = { Text(text = item.title) },
                        selected = index == selectedItemIndex ,
                        onClick = {
                            selectedItemIndex=index
                            scope.launch { drawerState.close() }
                            if (
                                item.title=="Sign Up"
                            ) {
                                // Handle Sign Up navigation action here

                            }
                        },
                        icon = {
                            Icon(imageVector = item.selectedIcon
                                , contentDescription =item.title )
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
                        IconButton(onClick = {

                          scope.launch {
                              if (drawerState.isClosed){
                                  drawerState.open()
                              }else {
                                  drawerState.close()
                              }
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Upcoming Medication",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                diseaseList.forEach { disease ->
                    DiseaseMedicationCard(disease = disease,
                        onDelete = {diseaseName ->
                            viewModel.deleteDisease(diseaseName)
                        },
                        onUpdate = {updateDisease ->
                            viewModel.updateDisease(diseaseName = disease.name,updateDisease)
                        }
                    )
                }
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




