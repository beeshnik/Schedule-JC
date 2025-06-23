package hph.app.schedulejc.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import hph.app.data.repository.ProfileRepositoryImplementation
import hph.app.data.repository.ScheduleRepositoryImplementation
import hph.app.domain.model.Profile
import hph.app.domain.repository.ProfileRepository
import hph.app.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileCreationVM(
    private val scheduleRepository: ScheduleRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Int>>(emptyList())
    val courses: StateFlow<List<Int>> = _courses

    private val _programs = MutableStateFlow<List<String>>(emptyList())
    val programs: StateFlow<List<String>> = _programs

    private val _groups = MutableStateFlow<List<String>>(emptyList())
    val groups: StateFlow<List<String>> = _groups

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _selectedCourse = MutableStateFlow<Int?>(null)
    val selectedCourse: StateFlow<Int?> = _selectedCourse

    private val _selectedProgram = MutableStateFlow<String?>(null)
    val selectedProgram: StateFlow<String?> = _selectedProgram

    private val _selectedGroup = MutableStateFlow<String?>(null)
    val selectedGroup: StateFlow<String?> = _selectedGroup

    private val _cardName = MutableStateFlow<String?>("")
    val cardName: StateFlow<String?> = _cardName

    fun setCardName(name: String) {
        _cardName.value = name
    }

    fun loadCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _courses.value = scheduleRepository.getCourse()
            } catch (e: Exception) {
                Log.e("ScheduleVM", "Ошикба: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPrograms(course: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _programs.value = scheduleRepository.getProgram(course = course)
            } catch (e: Exception) {
                Log.e("ScheduleVM", "Ошикба: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadGroups(course: Int, program: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _groups.value = scheduleRepository.getGroup(course = course, program = program)
            } catch (e: Exception) {
                Log.e("ScheduleVM", "Ошикба: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setSelectedCourse(course: Int) {
        _selectedCourse.value = course
        loadPrograms(course)
    }
    fun setSelectedProgram(program: String) {
        _selectedProgram.value = program
        if (selectedCourse.value != null && selectedProgram.value != null) {
            loadGroups(course = selectedCourse.value!!, program = selectedProgram.value!!)
        }
    }
    fun setSelectedGroup(group: String) {
        _selectedGroup.value = group
    }

    fun saveProfile() {
        val profile: Profile = Profile(
            group = selectedGroup.value!!,
            program = selectedProgram.value!!,
            course = selectedCourse.value!!,
            name = _cardName.value!!,
        )
        viewModelScope.launch {
            profileRepository.saveProfile(profile)
        }
    }
}