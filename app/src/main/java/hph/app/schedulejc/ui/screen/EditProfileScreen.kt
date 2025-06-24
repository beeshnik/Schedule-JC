package hph.app.schedulejc.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import hph.app.domain.model.ProfileDomainEntity
import hph.app.schedulejc.ui.viewmodel.EditProfileVM
import hph.app.schedulejc.ui.viewmodel.EditProfileVMFactory

@Composable
fun EditProfileScreen(navController: NavController, profileId: String?) {
    val context = LocalContext.current
    val viewModel: EditProfileVM = viewModel(
        factory = EditProfileVMFactory(context = context)
    )
    val profile by viewModel.profile.collectAsState()

    LaunchedEffect(Unit) { viewModel.getProfile(profileId) }

    ScheduleJCTheme {
        Scaffold(
            bottomBar = {
                NavigationButtons(
                    onNext = {
                        viewModel.updateProfile()
                        navController.navigate("main")
                    },
                    nextEnabled = true
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                FinalConfirmation(
                    modifier = Modifier.padding(16.dp),
                    profile = profile,
                    setName = { viewModel.setCardName(it) },
                    setColor = { viewModel.setCardColor(it) }
                )
            }
        }
    }
}

@Composable
private fun NavigationButtons(
    onNext: () -> Unit,
    nextEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onNext,
            enabled = nextEnabled,
            modifier = Modifier.weight(1f)
        ) {
            Text("Готово")
        }
    }
}

@Composable
private fun FinalConfirmation(
    modifier: Modifier = Modifier,
    profile: ProfileDomainEntity?,
    setName: (String) -> Unit,
    setColor: (Int) -> Unit
) {
    val initialName = profile?.name ?: ""
    val initialColor = profile?.color?.toLong() ?: 0xFFFFEBEE

    var cardName by remember { mutableStateOf(profile?.name ?: "") }
    val isError by remember { mutableStateOf(false) }
    val colors = listOf(
        0xFFFFEBEE,
        0xFFE3F2FD,
        0xFFE8F5E9,
        0xFFFFF8E1,
        0xFFF3E5F5,
        0xFFECEFF1
    )
    var selectedColor by remember { mutableStateOf(colors[0]) }

    LaunchedEffect(profile) {
        profile?.let {
            cardName = it.name
            setName(it.name)
            selectedColor = it.color.toLong()
            setColor(it.color.toInt())
        }
    }

    Column(modifier) {
        Text("Изменение профиля", style = MaterialTheme.typography.titleLarge)

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

        Text("Цвет карточки:", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            colors.forEach { color ->
                ColorSelectionItem(
                    color = color,
                    isSelected = color == selectedColor,
                    onSelect = {
                        selectedColor = color
                        setColor(color.toInt())
                    }
                )
            }
        }

        InfoRow("Курс:", profile?.course.toString() ?: "Не выбран")
        InfoRow("Направление:", profile?.program.toString() ?: "Не выбрано")
        InfoRow("Группа:", profile?.group.toString() ?: "Не выбрана")
    }
}

@Composable
private fun ColorSelectionItem(
    color: Long,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(color))
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .clickable(onClick = onSelect)
    )
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

