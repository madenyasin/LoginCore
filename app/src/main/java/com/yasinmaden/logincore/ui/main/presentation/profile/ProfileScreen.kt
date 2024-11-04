package com.yasinmaden.logincore.ui.main.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yasinmaden.logincore.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun ProfileScreen(
    uiState: ProfileContract.UiState,
    uiEffect: Flow<ProfileContract.UiEffect>,
    onAction: (ProfileContract.UiAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    HandleProfileUiEffects(
        uiEffect = uiEffect,
        navController = navController
    )

    ProfileContent(
        uiState = uiState,
        uiEffect = uiEffect,
        onAction = onAction,
        modifier = modifier
    )
}

@Composable
fun ProfileContent(
    uiState: ProfileContract.UiState,
    uiEffect: Flow<ProfileContract.UiEffect>,
    onAction: (ProfileContract.UiAction) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)

    ) {
        Card {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.user_profile),
                        contentDescription = "Profile Image",
                        Modifier.size(70.dp)
                    )

                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Profile",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .background(
                                color = Color.LightGray,
                                shape = CircleShape
                            )
                            .padding(4.dp)

                    )
                }
                Column {

                    Text(text = "Your Name", style = MaterialTheme.typography.titleLarge)
                    Text(text = "yourname@mail.com", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        HorizontalDivider()
        ElevatedCard {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Name", style = MaterialTheme.typography.titleMedium)
                Text("Yourname", style = MaterialTheme.typography.titleMedium)
            }
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Email", style = MaterialTheme.typography.titleMedium)
                Text("yourname@mail.com", style = MaterialTheme.typography.titleMedium)
            }
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Phone Number", style = MaterialTheme.typography.titleMedium)
                Text("Add Number", style = MaterialTheme.typography.titleMedium)
            }
        }
        HorizontalDivider()
        OutlinedCard(
            Modifier.clickable {
                // TODO: Navigate to settings screen
            }
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Settings", style = MaterialTheme.typography.titleMedium)
                Box(Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go Settings",
                        Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }

        OutlinedCard(
            Modifier.clickable {
                // TODO: Navigate to notifications screen
            }
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Notifications", style = MaterialTheme.typography.titleMedium)
                Box(Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go Notifications",
                        Modifier.align(Alignment.CenterEnd)
                    )

                }
            }
        }
        OutlinedCard(
            Modifier.clickable {
                onAction.invoke(ProfileContract.UiAction.Logout)
            }
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_logout),
                    contentDescription = "Log Out",
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Log Out", style = MaterialTheme.typography.titleMedium)
                Box(Modifier.weight(1f)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Go Log Out",
                        Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }


    }


}

@Composable
fun HandleProfileUiEffects(
    uiEffect: Flow<ProfileContract.UiEffect>,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(true) {
        uiEffect.collect { effect ->
            when (effect) {
                ProfileContract.UiEffect.NavigateToLogin -> {
                    navController.navigate("login") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }

                is ProfileContract.UiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        uiState = ProfileContract.UiState(),
        uiEffect = flowOf(),
        onAction = {},
        navController = NavController(LocalContext.current)
    )
}