package hph.app.schedulejc.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hph.app.data.repository.ProfileRepositoryImplementation
import hph.app.data.repository.ScheduleRepositoryImplementation

class ProfileCreationVMFactory(context: Context) : ViewModelProvider.Factory {

    private val profileRepository: ProfileRepositoryImplementation = ProfileRepositoryImplementation(context = context)
    private val scheduleRepository: ScheduleRepositoryImplementation = ScheduleRepositoryImplementation()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileCreationVM(
            scheduleRepository = scheduleRepository,
            profileRepository = profileRepository
        ) as T
    }
}