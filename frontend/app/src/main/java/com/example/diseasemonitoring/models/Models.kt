package com.example.diseasemonitoring.models

data class Disease(
    val name: String,
    val symptoms: String,
    val severity: String,
    val notes: String,
    val treatment: Treatment,
    val status: String,
    val diagnosedAt: String
)

data class Treatment(
    val medications: List<Medication>,
    val lifestyleRecommendations: String
)

data class Medication(
    val name: String,
    val dosage: String,
    val frequency: String,
    val duration: String
)