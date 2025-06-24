package hph.app.schedulejc.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
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
            when {
                profiles.size == 0 -> {
                    Column (
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Нажмите на '+', чтобы добавить расписание")
                        }
                    }
                }
                profiles.size > 0 -> {
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
                                navController = navController,
                                onDelete = {viewModel.deleteProfile(it)}
                            )
                        }
                    }

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
    navController: NavController,
    onDelete: (id: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var anchorPosition by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("getSchedule/${profile.id}")
                },
            colors = CardDefaults.cardColors(
                containerColor = Color(profile.color)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                        modifier = Modifier.weight(1f)
                    )

                    Box {
                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            anchorPosition = it
                                            expanded = true
                                        }
                                    )
                                }
                        ) {
                            Icon(
                                Icons.Default.MoreVert,
                                contentDescription = "More options"
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            offset = DpOffset(
                                x = 0.dp,
                                y = 0.dp
                            )
                        ) {
                            DropdownMenuItem(
                                text = { Text("Редактировать") },
                                onClick = {
                                    expanded = false
                                    navController.navigate("editProfile/${profile.id}")
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Удалить", color = MaterialTheme.colorScheme.error) },
                                onClick = {
                                    expanded = false
                                    onDelete(profile.id)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Курс: ${profile.course}")
                    Text(text = profile.group)
                    Text(text = "Направление: ${profile.program}")
                }
            }
        }
    }
}