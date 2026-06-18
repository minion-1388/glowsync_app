package com.example.glowsync.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.glowsync.screens.HomeScreen
import com.example.glowsync.screens.ProductsScreen
import com.example.glowsync.screens.ProfileScreen
import com.example.glowsync.screens.RoutineScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val destinations = listOf(
        Destination("home", "Início", "⌂"),
        Destination("products", "Produtos", "♡"),
        Destination("routine", "Rotina", "☀"),
        Destination("profile", "Perfil", "●")
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Text(destination.symbol) },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, "home", Modifier.padding(innerPadding)) {
            composable("home") { HomeScreen() }
            composable("products") { ProductsScreen() }
            composable("routine") { RoutineScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}

private data class Destination(val route: String, val label: String, val symbol: String)
