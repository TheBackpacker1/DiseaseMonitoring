import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.diseasemonitoring.models.Disease
import com.example.diseasemonitoring.models.PrescriptionTimes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDiseaseDialog(onDismiss: () -> Unit, onAddDisease: (Disease) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(16.dp),
            tonalElevation = 4.dp
        ) {
            var diseaseName by remember { mutableStateOf("") }
            var medicine by remember { mutableStateOf("") }
            var prescriptionTimes by remember { mutableStateOf(PrescriptionTimes(false, false, false)) }
            var prescription by remember { mutableStateOf("") }
            var notificationTime by remember { mutableStateOf("") }
            var diagnosedAt by remember { mutableStateOf("") }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Add Disease and Medication",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = diseaseName,
                    onValueChange = { diseaseName = it },
                    label = { Text("Disease Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                OutlinedTextField(
                    value = medicine,
                    onValueChange = { medicine = it },
                    label = { Text("Medicine Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                // Prescription Times
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = prescriptionTimes.morning,
                        onCheckedChange = { prescriptionTimes = prescriptionTimes.copy(morning = it) },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text("Morning", style = MaterialTheme.typography.bodyMedium)

                    Checkbox(
                        checked = prescriptionTimes.afternoon,
                        onCheckedChange = { prescriptionTimes = prescriptionTimes.copy(afternoon = it) },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text("Afternoon", style = MaterialTheme.typography.bodyMedium)

                    Checkbox(
                        checked = prescriptionTimes.night,
                        onCheckedChange = { prescriptionTimes = prescriptionTimes.copy(night = it) },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text("Night", style = MaterialTheme.typography.bodyMedium)
                }

                // Prescription and Notification Time
                OutlinedTextField(
                    value = prescription,
                    onValueChange = { prescription = it },
                    label = { Text("Prescription Details") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                OutlinedTextField(
                    value = notificationTime,
                    onValueChange = { notificationTime = it },
                    label = { Text("Notification Time") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            // Validation check
                            if (diseaseName.isNotEmpty() && medicine.isNotEmpty()) {
                                val newDisease = Disease(
                                    userId = "", // Assuming this will be set later
                                    name = diseaseName,
                                    medicine = medicine,
                                    prescriptionTimes = prescriptionTimes,
                                    prescription = prescription,
                                    notificationTime = notificationTime,
                                    diagnosedAt = diagnosedAt // Use diagnosedAt instead of empty string
                                )

                                onAddDisease(newDisease)
                                onDismiss() // Dismiss after adding
                            }
                        },
                        modifier = Modifier.padding(start = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Add Disease", color = Color.White)
                    }
                }
            }
        }
    }
}