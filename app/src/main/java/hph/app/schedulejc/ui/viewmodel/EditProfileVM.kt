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

class EditProfileVM(
    private val scheduleRepository: ScheduleRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {


    private val _profile = MutableStateFlow<ProfileDomainEntity?>(null)
    val profile: StateFlow<ProfileDomainEntity?> = _profile

    private val _cardName = MutableStateFlow<String?>("")
    val cardName: StateFlow<String?> = _cardName

    fun setCardName(name: String) {
        _cardName.value = name
    }

    private val _selectedColor = MutableStateFlow(profile.value?.color ?: 0xFFFAFAFA.toInt())
    val selectedColor: StateFlow<Int> = _selectedColor

    fun setCardColor(color: Int) {
        _selectedColor.value = color
    }

    fun getProfile(id: String?) {
        viewModelScope.launch {
            if (id != null) {
                _profile.value = profileRepository.getProfileById(id.toInt())
            }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            val newProfile = ProfileDomainEntity(
                name = _cardName.value!!,
                color = _selectedColor.value,
                group = profile.value?.group!!,
                course = profile.value?.course!!,
                createdAt = profile.value?.createdAt!!,
                id = profile.value?.id!!,
                program = profile.value?.program!!
            )
            profileRepository.updateProfile(profile = newProfile)
        }
    }
}