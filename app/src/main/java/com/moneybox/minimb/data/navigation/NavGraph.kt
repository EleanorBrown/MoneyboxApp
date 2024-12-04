package com.moneybox.minimb.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moneybox.minimb.data.login.LoginScreen
import com.moneybox.minimb.data.planvalue.PlanValueScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("planValue/{token}") { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            PlanValueScreen(token)
        }
    }
}