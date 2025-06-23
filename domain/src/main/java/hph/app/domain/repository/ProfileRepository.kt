package hph.app.domain.repository

import hph.app.domain.model.Profile
import hph.app.domain.model.ProfileDomainEntity

interface ProfileRepository {
    suspend fun saveProfile(profile: Profile)
    suspend fun loadProfiles(): List<ProfileDomainEntity>
    suspend fun getProfileById(id: Int): ProfileDomainEntity?
}