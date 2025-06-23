package hph.app.schedulejc.ui.screen

import android.util.Log
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hph.app.domain.model.Profile
import hph.app.domain.model.ProfileDomainEntity
import hph.app.schedulejc.ui.theme.ScheduleJCTheme
import hph.app.schedulejc.ui.viewmodel.MainScreenVM
import hph.app.schedulejc.ui.viewmodel.MainScreenVMFactory

@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: MainScreenVM = viewModel(
        factory = MainScreenVMFactory(context = context)
    )

    val profiles by viewModel.profiles.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfiles()
    }

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
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(profiles.size) { profile ->
                    ProfileCard(
                        profile = profiles[profile],
                        navController = navController
                    )
                }
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

@Composable
fun ProfileCard(
    profile: ProfileDomainEntity,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Card(
        modifier = modifier,
        onClick = {
            navController.navigate("getSchedule/${profile.id}")
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = profile.name,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Курс: ${profile.course}")
                Text(text = profile.group)
                Text(text = "Направление: ${profile.program}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Обновлено:")
                Text(text = "Сегодня в 21:48")
            }
        }
    }
}