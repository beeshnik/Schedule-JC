package hph.app.schedulejc.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import hph.app.data.repository.ProfileRepositoryImplementation
import hph.app.data.repository.ScheduleRepositoryImplementation
import hph.app.domain.model.Lesson
import hph.app.domain.model.Profile
import hph.app.domain.model.ProfileDomainEntity
import hph.app.domain.repository.ProfileRepository
import hph.app.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GetScheduleVM(
    private val scheduleRepository: ScheduleRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _lessons = MutableStateFlow<List<Lesson>>(emptyList())
    val lessons: StateFlow<List<Lesson>> = _lessons

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _profile = MutableStateFlow<ProfileDomainEntity?>(null)
    val profile: StateFlow<ProfileDomainEntity?> = _profile

    fun loadLessons(id: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (id != null) {
                    _profile.value = profileRepository.getProfileById(id.toInt())
                    if (profile.value != null) {
                        _lessons.value = scheduleRepository.getSchedule(profile.value!!.group)
                    }
                }
            } catch (e: Exception) {
                // Обработка ошибок
                Log.e("GetScheduleVM", "Error loading schedule", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}