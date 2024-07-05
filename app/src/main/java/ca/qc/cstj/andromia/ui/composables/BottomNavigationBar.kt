package ca.qc.cstj.andromia.ui.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ca.qc.cstj.andromia.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.User,
        BottomNavItem.Allies,
        BottomNavItem.Portals,
        BottomNavItem.Boosters
    )

    NavigationBar {
        items.forEach {
            AddItem(screen = it, navController)
        }
    }

}

@Composable
fun RowScope.AddItem(screen: BottomNavItem, navController: NavController) {
    NavigationBarItem(
        label = { Text(text = screen.title) },
        selected = true,
        onClick = { navController.navigate(screen.route) },
        icon = { },
        colors = NavigationBarItemDefaults.colors()
    )
}

sealed class BottomNavItem(var title: String, var route: String) {
    object Home : BottomNavItem("Home", route = "home")
    object User : BottomNavItem("Explorer", route = "user")
    object Allies : BottomNavItem("Allies", route = "allies")
    object Portals : BottomNavItem("Portals", route = "portals")
    object Boosters : BottomNavItem("Boosters", route = "boosters")

}