package com.example.diseasemonitoring.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diseasemonitoring.viewmodels.DiseaseViewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diseasemonitoring.models.Disease
import com.example.diseasemonitoring.models.Treatment

@Composable


fun DiseaseScreen() {

    val viewModel : DiseaseViewModels = viewModel()
    val disease by viewModel.disease.observeAsState(initial = null)

    var diseaseName by remember { mutableStateOf("") }
    var symptoms by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var diagnosedAt by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {


        TextField(
            value = diseaseName,
            onValueChange = { diseaseName = it },
            label = { Text("Disease Name") }
        )
        TextField(
            value = symptoms,
            onValueChange = { symptoms = it },
            label = { Text("Symptoms") }
        )
        TextField(
            value = severity,
            onValueChange = { severity = it },
            label = { Text("Severity") }
        )
        TextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") }
        )
        TextField(
            value = status,
            onValueChange = { status = it },
            label = { Text("Status") }
        )
        TextField(
            value = diagnosedAt,
            onValueChange = { diagnosedAt = it },
            label = { Text("Diagnosed At (YYYY-MM-DD)") }
        )

        // Button to add the disease
        Button(onClick = {
            val newDisease = Disease(
                name = diseaseName,
                symptoms = symptoms,
                severity = severity,
                notes = notes,
                status = status,
                diagnosedAt = diagnosedAt,
                // Assuming Treatment is another data class. Adjust accordingly.
                treatment = Treatment(medications = emptyList(), lifestyleRecommendations = "")
            )
            viewModel.addDisease(newDisease)
        }) {
            Text(text = "Add Disease")
        }

    }
    disease?.let {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp) // Add some spacing between texts
        ) {
            Text(text = "Name: ${it.name}")
            Text(text = "Symptoms: ${it.symptoms}")
            Text(text = "Severity: ${it.severity}")
            Text(text = "Notes: ${it.notes}")
            Text(text = "Status: ${it.status}")
            Text(text = "Diagnosed At: ${it.diagnosedAt}")

            // Display treatment information
            it.treatment.let { treatment ->
                Text(text = "Treatment:")
                treatment.medications.forEach { medication ->
                    Text(text = "Medication: ${medication.name}, Dosage: ${medication.dosage}, Frequency: ${medication.frequency}, Duration: ${medication.duration}")
                }
                Text(text = "Lifestyle Recommendations: ${treatment.lifestyleRecommendations}")
            }
        }} ?: Text(text = "No disease found")

}