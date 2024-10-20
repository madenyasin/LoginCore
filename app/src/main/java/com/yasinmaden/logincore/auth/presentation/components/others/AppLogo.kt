package com.yasinmaden.logincore.auth.presentation.components.others

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yasinmaden.logincore.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier
){
    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.ic_home_app_logo),
        contentDescription = "Home App Logo",
        modifier = Modifier.size(125.dp)
    )
}