package hph.app.domain.repository

import hph.app.domain.model.Profile

interface ProfileRepository {
    suspend fun saveProfile(profile: Profile)
    suspend fun loadProfile(): Profile
}