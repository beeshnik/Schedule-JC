package hph.app.schedulejc.ui.screen

import android.security.identity.AccessControlProfileId
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hph.app.domain.model.Lesson
import hph.app.domain.model.LessonType
import hph.app.schedulejc.ui.theme.ScheduleJCTheme
import hph.app.schedulejc.ui.viewmodel.GetScheduleVM
import hph.app.schedulejc.ui.viewmodel.GetScheduleVMFactory
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun GetScheduleScreen(navController: NavController, profileId: String?) {

    val context = LocalContext.current
    val viewModel: GetScheduleVM = viewModel(
        factory = GetScheduleVMFactory(context = context)
    )

    val lessons by viewModel.lessons.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadLessons(profileId) }

    ScheduleJCTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(lessons) { lesson ->
                    Lesson(lesson = lesson)
                }
            }
        }
    }
}

@Composable
fun Lesson(
    lesson: Lesson,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Заголовок с названием предмета и типом занятия
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lesson.subject,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = lesson.lessonType.type,
                    style = MaterialTheme.typography.labelLarge,
                    color = when (lesson.lessonType) {
                        LessonType.LECTURE -> Color.Blue
                        LessonType.SEMINAR -> Color.Green
                        LessonType.EXAM -> Color.Red
                        LessonType.TEST -> Color.Magenta
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Время занятия
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Время",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${lesson.time.startTime} - ${lesson.time.endTime}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Дата и день недели
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Дата",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${lesson.time.date} (${lesson.time.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())})",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Преподаватель (если есть)
            lesson.lecturer?.let { lecturer ->
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Преподаватель",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = lecturer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Место проведения (если есть)
            lesson.places?.let { places ->
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (lesson.isOnline()) Icons.Default.Info else Icons.Default.LocationOn,
                        contentDescription = "Место",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (lesson.isOnline()) {
                            "Онлайн"
                        } else {
                            places.joinToString { place ->
                                "Корп. ${place.building ?: "?"}, ауд. ${place.office ?: "?"}"
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Дополнительная информация (если есть)
            lesson.additionalInfo?.let { info ->
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    info.forEach { item ->
                        Text(
                            text = "• $item",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}