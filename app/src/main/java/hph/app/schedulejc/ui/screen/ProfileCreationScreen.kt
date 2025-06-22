package hph.app.schedulejc.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hph.app.schedulejc.ui.theme.ScheduleJCTheme
import hph.app.schedulejc.ui.viewmodel.ProfileCreationVM

@Composable
fun ProfileCreationScreen() {
    val viewModel: ProfileCreationVM = viewModel()
    val courses by viewModel.courses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    viewModel.loadCourses()

    ScheduleJCTheme {
        Scaffold(
        ) { padding ->
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.padding(padding),
                    columns = GridCells.Adaptive(minSize = 128.dp)
                ) {
                    items(courses) { course ->
                        Text(course.toString())
                    }

                }
            }

        }
    }
}