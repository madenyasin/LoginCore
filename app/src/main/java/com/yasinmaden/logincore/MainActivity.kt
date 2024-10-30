package com.yasinmaden.logincore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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

            LoginCoreTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavBar(
                            navController = navController,
                            onItemClick = {item->
                                navController.navigate(item.route) {
                                    popUpTo(item.route) { inclusive = true } // Önceki kopyaları temizle
                                    launchSingleTop = true
                                    restoreState = true // Önceki durum korunur
                                    //todo: doğru mu tam bilmiyorum
                                }
                            }
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