package com.vaultguard.app.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaultguard.app.R
import kotlinx.coroutines.delay

@Composable

fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )
    
    // Bouncy Scale Animation
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = spring(
            dampingRatio = 0.4f, // High Bounce
            stiffness = Spring.StiffnessLow
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000) // Show splash for 2 seconds
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F7)), // Swiss Minimal Light Gray
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher), // App Icon
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .scale(scaleAnim.value)
                    .alpha(alphaAnim.value)
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            // Animated Text
            androidx.compose.animation.AnimatedVisibility(
                visible = startAnimation,
                enter = androidx.compose.animation.slideInVertically(
                    initialOffsetY = { 50 } // Slide up from bottom
                ) + androidx.compose.animation.fadeIn(
                    animationSpec = tween(1000)
                )
            ) {
                Text(
                    text = "ZeroKeep",
                    color = Color.Black, // Dark Text for Light Theme
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}
