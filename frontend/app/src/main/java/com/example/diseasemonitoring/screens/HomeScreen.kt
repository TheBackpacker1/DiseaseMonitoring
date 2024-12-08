package com.example.diseasemonitoring.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diseasemonitoring.models.Disease
import com.example.diseasemonitoring.screens.views.DiseaseMedicationCard
import com.example.diseasemonitoring.viewmodels.DiseaseViewModels

@Composable
fun HomeScreen(
    diseaseList: List<Disease>,
    viewModel: DiseaseViewModels,
    showAddDiseaseDialog: Boolean,
    onShowAddDiseaseDialogChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            DiseaseMedicationCard(
                disease = disease,
                onDelete = { diseaseName ->
                    viewModel.deleteDisease(diseaseName)
                },
                onUpdate = { updateDisease ->
                    viewModel.updateDisease(diseaseName = disease.name, updateDisease)
                }
            )
        }
    }
}
