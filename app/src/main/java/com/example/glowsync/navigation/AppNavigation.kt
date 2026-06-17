package com.example.glowsync.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.glowsync.screens.HomeScreen
import com.example.glowsync.screens.ProductsScreen
import com.example.glowsync.screens.ProfileScreen
import com.example.glowsync.screens.RoutineScreen

@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){

        composable("home"){
            HomeScreen()
        }

        composable("products"){
            ProductsScreen()
        }

        composable("routine"){
            RoutineScreen()
        }

        composable("profile"){
            ProfileScreen()
        }

    }

}