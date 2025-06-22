package hph.app.schedulejc.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hph.app.schedulejc.ui.theme.ScheduleJCTheme

@Composable
fun MainScreen(navController: NavController) {
    ScheduleJCTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                NavButton(
                    modifier = Modifier.absoluteOffset(),
                    navController = navController
                )
            }
        ) { padding ->
            LazyVerticalGrid(
                modifier = Modifier.padding(padding),
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {

            }

        }
    }
}

@Composable
fun NavButton(
    modifier: Modifier = Modifier,
    navController: NavController
){
    FloatingActionButton(
        onClick = {
            navController.navigate("createRoute")
        },
        modifier = modifier
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}