package com.example.gymapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapp.app.presentation.screen.AddNewMemberScreen
import com.example.gymapp.app.presentation.screen.DashboardScreen
import com.example.gymapp.app.presentation.screen.LoginScreen
import com.example.gymapp.app.presentation.screen.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import com.example.gymapp.app.presentation.viewmodel.MainViewModel
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GymApp : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash") {
                composable("splash") { SplashScreen(navController) }
                composable("login") { LoginScreen(navController) }
                composable("dashboard") { DashboardScreen(navController, hiltViewModel<MainViewModel>()) }
                composable("add_new_member") { AddNewMemberScreen(navController = navController)}
            }
        }
    }
}