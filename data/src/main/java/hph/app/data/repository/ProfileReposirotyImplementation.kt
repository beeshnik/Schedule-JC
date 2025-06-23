package hph.app.data.repository

import android.content.Context
import hph.app.data.local.db.AppDatabase
import hph.app.data.local.db.entities.ProfileEntity
import hph.app.domain.model.Profile
import hph.app.domain.model.ProfileDomainEntity
import hph.app.domain.repository.ProfileRepository

class ProfileRepositoryImplementation(context: Context) : ProfileRepository {
    private val db = AppDatabase.getInstance(context)
    private val dao = db.profileDao()

    override suspend fun saveProfile(profile: Profile) {
        dao.saveProfile(profile.toEntity())
    }

    override suspend fun loadProfiles(): List<ProfileDomainEntity> {
        return dao.getAllProfiles().map {
            it.toDomain()
        }
    }

    override suspend fun getProfileById(id: Int): ProfileDomainEntity? {
        return dao.getProfileById(id)?.toDomain()
    }

    private fun Profile.toEntity() = ProfileEntity(
        course = course,
        program = program,
        group = group,
        name = name
    )

    private fun ProfileEntity.toDomain() = ProfileDomainEntity(
        course = course,
        program = program,
        group = group,
        name = name,
        id = id
    )
}