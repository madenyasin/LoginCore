package com.yasinmaden.logincore.main.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NavBar(
    selectedItem: Int,
    currentRoute: String?,
    onItemClick: (Int, NavBarItems) -> Unit
) {
    if (navBarItems.any { it.route == currentRoute }) {
    NavigationBar {
        navBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemClick(index, item) },
                icon = {
                    Icon(
                        imageVector = if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) }
            )
        }
    }
    }
}


@Composable
@Preview(showBackground = true)
fun NavBarPreview() {
    var selectedItem by remember { mutableStateOf(0) }
    NavBar(selectedItem, currentRoute = "home", onItemClick = { index, item ->
        selectedItem = index
    })
}