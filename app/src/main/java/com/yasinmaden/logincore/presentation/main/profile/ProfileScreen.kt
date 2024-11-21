package com.yasinmaden.logincore.presentation.main.profile

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.yasinmaden.logincore.R
import com.yasinmaden.logincore.presentation.navigation.Screen
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
        onAction = onAction,
        modifier = modifier
    )
}

@Composable
fun ProfileContent(
    uiState: ProfileContract.UiState,
    onAction: (ProfileContract.UiAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(true) {
        onAction(ProfileContract.UiAction.LoadProfile)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        ProfileCard(
            uiState = uiState,
            onAction = onAction
        )

        HorizontalDivider()
        ProfileOptions(
            icon = Icons.Outlined.Settings,
            text = "Settings",
            onClick = { /* TODO: Navigate to settings screen */ }
        )
        ProfileOptions(
            icon = Icons.Outlined.Notifications,
            text = "Notifications",
            onClick = { /* TODO: Navigate to notifications screen */ }
        )
        ProfileOptions(
            icon = ImageVector.vectorResource(R.drawable.ic_sign_out),
            text = "Sign Out",
            onClick = { onAction.invoke(ProfileContract.UiAction.SignOut) }
        )
    }
}

@Composable
fun ProfileCard(
    uiState: ProfileContract.UiState,
    onAction: (ProfileContract.UiAction) -> Unit,
) {
    Card {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)

            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                ) {
                    if (uiState.profileImageUrl != "null") {
                        AsyncImage(
                            model = uiState.profileImageUrl,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    } else {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.user_profile),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                        )
                    }
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-8).dp, y = (-8).dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Profile",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Column {
                Text(text = uiState.name, style = MaterialTheme.typography.titleLarge)
                Text(text = uiState.email, style = MaterialTheme.typography.bodyMedium)
                Text(uiState.phoneNumber, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun ProfileOptions(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        Modifier.clickable { onClick() }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = text)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, style = MaterialTheme.typography.titleMedium)
            Box(Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Navigate to $text",
                    Modifier.align(Alignment.CenterEnd)
                )
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
                ProfileContract.UiEffect.NavigateToSignInScreen -> {
                    navController.navigate(Screen.SignIn.route) {
                        popUpTo(Screen.Home.route) {
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
        uiState = ProfileContract.UiState(
            name = "John Doe",
            email = "john.mclean@examplepetstore.com",
            phoneNumber = "123-456-7890",
            profileImageUrl = "null"
        ),
        uiEffect = flowOf(),
        onAction = {},
        navController = NavController(LocalContext.current)
    )
}