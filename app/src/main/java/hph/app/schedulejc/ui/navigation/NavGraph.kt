package hph.app.schedulejc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hph.app.schedulejc.ui.screen.MainScreen
import hph.app.schedulejc.ui.screen.ProfileCreationScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.Main.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Route.Main.route) {
            MainScreen(navController = navController)
        }

        composable(Route.CreateProfile.route) {
            ProfileCreationScreen()
        }

//        composable(
//            Route.Profile.route,
//            arguments = listOf(navArgument("userId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val userId = backStackEntry.arguments?.getString("userId")
//            ProfileScreen(userId)
//        }
    }
}