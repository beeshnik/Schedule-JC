package hph.app.schedulejc.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import hph.app.data.repository.ProfileRepositoryImplementation
import hph.app.data.repository.ScheduleRepositoryImplementation
import hph.app.domain.model.Profile
import hph.app.domain.model.ProfileDomainEntity
import hph.app.domain.repository.ProfileRepository
import hph.app.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenVM(
    private val scheduleRepository: ScheduleRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _profiles = MutableStateFlow<List<ProfileDomainEntity>>(emptyList())
    val profiles: StateFlow<List<ProfileDomainEntity>> = _profiles


    fun loadProfiles() {
        viewModelScope.launch {
            _profiles.value = profileRepository.loadProfiles()
            Log.e("ABC", "${profiles.value.size}")
        }
    }
}