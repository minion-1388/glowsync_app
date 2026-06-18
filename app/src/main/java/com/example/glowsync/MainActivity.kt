package com.example.glowsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.glowsync.navigation.AppNavigation
import com.example.glowsync.ui.theme.GlowsyncTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlowsyncTheme(dynamicColor = false) {
                AppNavigation()
            }
        }
    }
}
