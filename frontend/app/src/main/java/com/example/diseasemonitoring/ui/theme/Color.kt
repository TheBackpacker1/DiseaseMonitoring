package com.example.diseasemonitoring.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Define custom colors
val PrimaryColor = Color(0xFF6200EA) // Purple
val SecondaryColor = Color(0xFF03DAC5) // Teal
val BackgroundColor = Color(0xFFF2F2F2) // Light gray for a clean look
val SurfaceColor = Color(0xFFFFFFFF) // White surface for cards, dialogs
val OnPrimaryColor = Color.White
val OnSecondaryColor = Color.Black
val OnBackgroundColor = Color(0xFF333333) // Dark text on light background
val OnSurfaceColor = Color(0xFF333333) // Dark text on white surface

// Light theme colors
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = OnPrimaryColor,
    onSecondary = OnSecondaryColor,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor,
)

// Dark theme colors (optional, if you want a dark theme too)
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = Color(0xFF121212), // Dark background
    surface = Color(0xFF1E1E1E), // Darker surface
    onPrimary = OnPrimaryColor,
    onSecondary = OnSecondaryColor,
    onBackground = Color.White,
    onSurface = Color.White,
)