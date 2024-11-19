package com.example.diseasemonitoring.models

data class Disease(
    val userId: String,
    val name: String,
    val medicine: String,  // The name of the medicine (replacing the `Medication` list)
    val prescriptionTimes: PrescriptionTimes,
    val prescription: String,
    val notificationTime: String,
    val diagnosedAt: String,
)

data class PrescriptionTimes(
    val morning: Boolean,
    val afternoon: Boolean,
    val night: Boolean
)