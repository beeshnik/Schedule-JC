package hph.app.schedulejc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hph.app.schedulejc.ui.screen.EditProfileScreen
import hph.app.schedulejc.ui.screen.GetScheduleScreen
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
            ProfileCreationScreen(navController = navController)
        }

        composable(
            Route.GetSchedule.route,
            arguments = listOf(navArgument("profileId") { type = NavType.StringType })) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getString("profileId")
            GetScheduleScreen(navController = navController, profileId = profileId)
        }

        composable(
            Route.EditProfile.route,
            arguments = listOf(navArgument("profileId") { type = NavType.StringType })) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getString("profileId")
            EditProfileScreen(navController = navController, profileId = profileId)
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