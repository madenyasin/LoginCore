package com.yasinmaden.logincore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yasinmaden.logincore.main.presentation.components.NavBar
import com.yasinmaden.logincore.navigation.AppNavGraph
import com.yasinmaden.logincore.ui.theme.LoginCoreTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute by remember { mutableStateOf(navBackStackEntry?.destination?.route) }
            val selectedItem = remember { mutableIntStateOf(0) }

            LoginCoreTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavBar(
                            selectedItem = selectedItem.intValue,
                            currentRoute = currentRoute,
                            onItemClick = {index, item->
                                selectedItem.intValue = index
                            },
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        AppNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}