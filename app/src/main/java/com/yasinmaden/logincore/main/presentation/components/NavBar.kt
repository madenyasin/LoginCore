package com.yasinmaden.logincore.main.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavBar(
    navController: NavHostController,
    onItemClick: (NavBarItems) -> Unit,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    if (navBarItems.any { it.route == currentRoute }) {
        NavigationBar {
            navBarItems.forEachIndexed {_, item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = { onItemClick(item) },
                    icon = {
                        Icon(
                            imageVector = if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) }
                )
            }
        }
    }
}
