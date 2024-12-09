import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.diseasemonitoring.screens.Screen


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home" ) },
            label = { Text("Home") },
            selected = false, // Update selection based on navigation state
            onClick = { navController.navigate(Screen.Home.route) }
        )
        NavigationBarItem(
                icon = { Icon(Icons.Default.DateRange, contentDescription = "Doctor Appointments") },
        label = { Text("Doctor Appointments") },
        selected = false, // Update selection based on navigation state
            onClick = { navController.navigate(Screen.DoctorAppointments.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Person") },
            label = { Text("Person") },
            selected = false, // Update selection based on navigation state
            onClick = { navController.navigate(Screen.Profile.route) }
        )

    }
}
