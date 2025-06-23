package hph.app.schedulejc.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hph.app.schedulejc.ui.theme.ScheduleJCTheme
import hph.app.schedulejc.ui.viewmodel.ProfileCreationVM
import hph.app.schedulejc.ui.viewmodel.ProfileCreationVMFactory

@Composable
fun ProfileCreationScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: ProfileCreationVM = viewModel(
        factory = ProfileCreationVMFactory(context = context)
    )
    val courses by viewModel.courses.collectAsState()
    val programs by viewModel.programs.collectAsState()
    val groups by viewModel.groups.collectAsState()
    val selectedCourse by viewModel.selectedCourse.collectAsState()
    val selectedProgram by viewModel.selectedProgram.collectAsState()
    val selectedGroup by viewModel.selectedGroup.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var currentStep by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) { viewModel.loadCourses() }
    LaunchedEffect(courses) {
        if (selectedCourse == null && courses.isNotEmpty()) {
            viewModel.setSelectedCourse(courses[0])
        }
    }

    ScheduleJCTheme {
        Scaffold(
            bottomBar = {
                NavigationButtons(
                    currentStep = currentStep,
                    onNext = {
                        when(currentStep) {
                            1 -> if (selectedCourse != null) currentStep++
                            2 -> if (selectedProgram != null) currentStep++
                            3 -> if (selectedGroup != null) currentStep++
                            else -> if (
                                selectedGroup != null &&
                                selectedProgram != null &&
                                selectedCourse != null
                                ) {
                                viewModel.saveProfile()
                                navController.navigate("main")
                            }
                        }
                    },
                    onPrev = { if (currentStep > 1) currentStep-- },
                    nextEnabled = when(currentStep) {
                        1 -> selectedCourse != null
                        2 -> selectedProgram != null
                        3 -> selectedGroup != null
                        else -> true
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> CircularProgressIndicator()
                    currentStep == 1 -> CourseSelection(
                        courses = courses,
                        selectedCourse = selectedCourse,
                        onCourseSelected = { viewModel.setSelectedCourse(it) },
                        modifier = Modifier.padding(16.dp)
                    )
                    currentStep == 2 -> ProgramSelection(
                        programs = programs,
                        selectedProgram = selectedProgram,
                        onProgramSelected = { viewModel.setSelectedProgram(it) },
                        modifier = Modifier.padding(16.dp)
                    )
                    currentStep == 3 -> GroupSelection(
                        groups = groups,
                        selectedGroup = selectedGroup,
                        onGroupSelected = { viewModel.setSelectedGroup(it) },
                        modifier = Modifier.padding(16.dp)
                    )
                    else -> FinalConfirmation(
                        selectedCourse = selectedCourse,
                        selectedProgram = selectedProgram,
                        selectedGroup = selectedGroup,
                        modifier = Modifier.padding(16.dp),
                        setName = { viewModel.setCardName(it) }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigationButtons(
    currentStep: Int,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    nextEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (currentStep > 1) {
            Button(
                onClick = onPrev,
                modifier = Modifier.weight(1f)
            ) {
                Text("Назад")
            }
            Spacer(modifier = Modifier.width(16.dp))
        }

        Button(
            onClick = onNext,
            enabled = nextEnabled,
            modifier = Modifier.weight(1f)
        ) {
            Text(if (currentStep < 4) "Далее" else "Готово")
        }
    }
}

@Composable
private fun CourseSelection(
    courses: List<Int>,
    selectedCourse: Int?,
    onCourseSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text("Выберите курс", style = MaterialTheme.typography.titleLarge)
        courses.forEach { course ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = course == selectedCourse,
                        onClick = { onCourseSelected(course) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = course == selectedCourse,
                    onClick = null
                )
                Text("$course курс", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun ProgramSelection(
    programs: List<String>,
    selectedProgram: String?,
    onProgramSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text("Выберите курс", style = MaterialTheme.typography.titleLarge)
        programs.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = it == selectedProgram,
                        onClick = { onProgramSelected(it) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = it == selectedProgram,
                    onClick = null
                )
                Text(it, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun GroupSelection(
    groups: List<String>,
    selectedGroup: String?,
    onGroupSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text("Выберите группу", style = MaterialTheme.typography.titleLarge)
        groups.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = it == selectedGroup,
                        onClick = { onGroupSelected(it) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = it == selectedGroup,
                    onClick = null
                )
                Text(it, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}


@Composable
private fun FinalConfirmation(
    selectedCourse: Int?,
    selectedProgram: String?,
    selectedGroup: String?,
    modifier: Modifier = Modifier,
    setName: (String) -> Unit
) {
    var cardName by remember { mutableStateOf("") }
    val isError by remember { mutableStateOf(false) }

    Column(modifier) {
        Text("Подтверждение выбора", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = cardName,
            onValueChange = {
                cardName = it
                setName(it)
            },
            label = { Text("Название карточки") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            singleLine = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text("Введите название карточки", color = MaterialTheme.colorScheme.error)
                }
            }
        )

        InfoRow("Курс:", selectedCourse?.toString() ?: "Не выбран")
        InfoRow("Направление:", selectedProgram ?: "Не выбрано")
        InfoRow("Группа:", selectedGroup ?: "Не выбрана")
    }
}
@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.width(8.dp))
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

