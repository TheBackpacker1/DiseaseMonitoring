package com.example.diseasemonitoring.screens

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DoctorAppointments : Screen("doctor_appointments")
    object Profile : Screen("profile")
}