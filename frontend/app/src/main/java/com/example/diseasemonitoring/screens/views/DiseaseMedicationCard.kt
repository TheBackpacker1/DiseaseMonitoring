package com.example.diseasemonitoring.screens.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.diseasemonitoring.models.Disease

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseMedicationCard(disease: Disease ,
onDelete:(String) -> Unit,
onUpdate:(Disease) ->Unit

) {

    var showDialog =remember{ mutableStateOf(false) }
    var editableDisease = remember { mutableStateOf( disease.copy(
        name = disease.name ?: "",
        medicine = disease.medicine ?: "",
        prescription = disease.prescription ?: "",
        notificationTime = disease.notificationTime ?: "",
        diagnosedAt = disease.diagnosedAt ?: "",
        prescriptionTimes = disease.prescriptionTimes
    )) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {onDelete(disease.name)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
                IconButton(onClick = {showDialog.value=true}) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Update",
                        tint = Color.Blue
                    )
                }
            }


            Text(text = "Disease: ${disease.name}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Medication: ${disease.medicine}", style = MaterialTheme.typography.bodyMedium)

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (disease.prescriptionTimes.morning) {
                    Text("Morning", style = MaterialTheme.typography.bodyMedium)
                }
                if (disease.prescriptionTimes.afternoon) {
                    Text("Afternoon", style = MaterialTheme.typography.bodyMedium)
                }
                if (disease.prescriptionTimes.night) {
                    Text("Night", style = MaterialTheme.typography.bodyMedium)
                }
            }
            if (disease.prescription.isNotEmpty()) {
                Text(text = "Prescription: ${disease.prescription}", style = MaterialTheme.typography.bodyMedium)
            }

            // Notification Time
            if (disease.notificationTime.isNotEmpty()) {
                Text(text = "Notification Time: ${disease.notificationTime}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Edit Disease", style = MaterialTheme.typography.titleMedium) },
            text = {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Disease Name
                    OutlinedTextField(
                        value = editableDisease.value.name,
                        onValueChange = { editableDisease.value = editableDisease.value.copy(name = it) },
                        label = { Text("Disease Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Medication
                    OutlinedTextField(
                        value = editableDisease.value.medicine,
                        onValueChange = { editableDisease.value = editableDisease.value.copy(medicine = it) },
                        label = { Text("Medication") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Prescription Times
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = editableDisease.value.prescriptionTimes.morning,
                            onCheckedChange = { editableDisease.value = editableDisease.value.copy(prescriptionTimes = editableDisease.value.prescriptionTimes.copy(morning = it)) },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text("Morning", style = MaterialTheme.typography.bodyMedium)

                        Checkbox(
                            checked = editableDisease.value.prescriptionTimes.afternoon,
                            onCheckedChange = { editableDisease.value = editableDisease.value.copy(prescriptionTimes = editableDisease.value.prescriptionTimes.copy(afternoon = it)) },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text("Afternoon", style = MaterialTheme.typography.bodyMedium)

                        Checkbox(
                            checked = editableDisease.value.prescriptionTimes.night,
                            onCheckedChange = { editableDisease.value = editableDisease.value.copy(prescriptionTimes = editableDisease.value.prescriptionTimes.copy(night = it)) },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text("Night", style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Prescription
                    OutlinedTextField(
                        value = editableDisease.value.prescription,
                        onValueChange = { editableDisease.value = editableDisease.value.copy(prescription = it) },
                        label = { Text("Prescription") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Notification Time
                    OutlinedTextField(
                        value = editableDisease.value.notificationTime,
                        onValueChange = { editableDisease.value = editableDisease.value.copy(notificationTime = it) },
                        label = { Text("Notification Time") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )


                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editableDisease.value.name.isNotBlank() && editableDisease.value.medicine.isNotBlank()) {
                            onUpdate(editableDisease.value)
                            showDialog.value = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Update", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Cancel", color = Color.White)
                }
            }
        )
    }
}