package com.example.diseasemonitoring.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.material3.TextField
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
import com.example.diseasemonitoring.models.Medication
import com.example.diseasemonitoring.models.Treatment
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
                    DiseaseMedicationCard(disease = disease)
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

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false, // Update selection based on navigation state
            onClick = { /* Handle Home Navigation */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Create, contentDescription = "Reports") },
            label = { Text("Reports") },
            selected = false, // Update selection based on navigation state
            onClick = { /* Handle Profile Navigation */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Doctor Appointments") },
            label = { Text("Doctor Appointments") },
            selected = false, // Update selection based on navigation state
            onClick = { /* Handle Profile Navigation */ }
        )
    }
}

@Composable
fun DiseaseMedicationCard(disease: Disease ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = disease.name, style = MaterialTheme.typography.titleMedium)

            disease.treatment?.medications?.forEach { medication ->
                Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp)) {
                    Text(text = "Medication: ${medication.name}")
                    Text(text = "Dosage: ${medication.dosage}")
                    Text(text = "Frequency: ${medication.frequency}")
                    Text(text = "Duration: ${medication.duration}")
                }
            }
        }
    }
}

@Composable
fun AddDiseaseDialog(onDismiss: () -> Unit, onAddDisease: (Disease) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(16.dp),
            tonalElevation = 4.dp
        ) {
            var diseaseName by remember { mutableStateOf("") }
            var symptoms by remember { mutableStateOf("") }
            var severity by remember { mutableStateOf("") }
            var medicationName by remember { mutableStateOf("") }
            var dosage by remember { mutableStateOf("") }
            var frequency by remember { mutableStateOf("") }
            var duration by remember { mutableStateOf("") }
            var notes by remember { mutableStateOf("") }
            var status by remember { mutableStateOf("") }
            var diagnosedAt by remember { mutableStateOf("") }
            var lifestyleRecommendations by remember { mutableStateOf("") }
            var showDatePicker by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Add Disease and Medication",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                OutlinedTextField(
                    value = diseaseName,
                    onValueChange = { diseaseName = it },
                    label = { Text("Disease Name") },
                    leadingIcon = { Icon(Icons.Default.Create, contentDescription = null) }
                )
                OutlinedTextField(
                    value = symptoms,
                    onValueChange = { symptoms = it },
                    label = { Text("Symptoms") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) }
                )
                OutlinedTextField(
                    value = severity,
                    onValueChange = { severity = it },
                    label = { Text("Severity") },
                    leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) }
                )

                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

                OutlinedTextField(
                    value = medicationName,
                    onValueChange = { medicationName = it },
                    label = { Text("Medication Name") },
                    leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) }
                )
                OutlinedTextField(
                    value = dosage,
                    onValueChange = { dosage = it },
                    label = { Text("Dosage") }
                )
                OutlinedTextField(
                    value = frequency,
                    onValueChange = { frequency = it },
                    label = { Text("Frequency") }
                )
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration") }
                )

                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") }
                )
                OutlinedTextField(
                    value = status,
                    onValueChange = { status = it },
                    label = { Text("Status") }
                )


                OutlinedTextField(
                    value = diagnosedAt,
                    onValueChange = { diagnosedAt = it },
                    label = { Text("Diagnosed At") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    readOnly = true,
                    modifier = Modifier.clickable { showDatePicker = true }
                )

                if (showDatePicker) {
                    ShowDatePickerDialog(
                        onDismiss = { showDatePicker = false },
                        onDateSelected = { date ->
                            diagnosedAt = date
                            showDatePicker = false
                        }
                    )
                }
                OutlinedTextField(
                    value = lifestyleRecommendations,
                    onValueChange = { lifestyleRecommendations = it },
                    label = { Text("Lifestyle Recommendations") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            Log.d("AddDiseaseDialog", "Disease Name: $diseaseName")
                            Log.d("AddDiseaseDialog", "Medication Name: $medicationName")
                            Log.d("AddDiseaseDialog", "Submitting Disease...")
                            // Validation check
                            if (diseaseName.isNotEmpty() && medicationName.isNotEmpty()) {
                                val newDisease = Disease(
                                    name = diseaseName,
                                    symptoms = symptoms,
                                    severity = severity,
                                    notes = notes,
                                    status = status,
                                    diagnosedAt = diagnosedAt,
                                    treatment = Treatment(
                                        medications = listOf(
                                            Medication(
                                                name = medicationName,
                                                dosage = dosage,
                                                frequency = frequency,
                                                duration = duration
                                            )
                                        ),
                                        lifestyleRecommendations = lifestyleRecommendations
                                    )
                                )
                                onAddDisease(newDisease)
                                        Log.d("AddDiseaseDialog", "Disease added successfully")

                                onDismiss() // Dismiss after adding
                            } else {
                                Log.e("AddDiseaseDialog", "Error: Disease Name or Medication Name cannot be empty")

                            }
                        },
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Save", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowDatePickerDialog(onDismiss: () -> Unit, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Use a LaunchedEffect to show the dialog
    LaunchedEffect(Unit) {
        // Create the DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Format the selected date
                val selectedDate = "$dayOfMonth/${month + 1}/$year" // Format date as needed
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Show the dialog
        datePickerDialog.show()

        // Set dismiss listener
        datePickerDialog.setOnDismissListener { onDismiss() }
    }
}
