package hph.app.schedulejc.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hph.app.data.repository.ScheduleRepositoryImplementation
import hph.app.domain.model.Course
import hph.app.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileCreationVM(
    private val repository: ScheduleRepository = ScheduleRepositoryImplementation()
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Int>>(emptyList())
    val courses: StateFlow<List<Int>> = _courses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _courses.value = repository.getCourse()
            } catch (e: Exception) {
                Log.e("ScheduleVM", "Ошикба: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}