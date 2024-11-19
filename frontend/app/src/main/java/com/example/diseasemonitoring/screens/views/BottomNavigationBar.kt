import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


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
