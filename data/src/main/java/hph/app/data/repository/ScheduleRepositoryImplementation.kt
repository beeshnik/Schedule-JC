package hph.app.data.repository

import hph.app.data.remote.api.RetrofitClient
import hph.app.data.remote.api.ScheduleApi
import hph.app.domain.model.Course
import hph.app.domain.model.Group
import hph.app.domain.model.Program
import hph.app.domain.repository.ScheduleRepository

class ScheduleRepositoryImplementation(
    private val scheduleApi: ScheduleApi = RetrofitClient.scheduleApi
): ScheduleRepository {
    override suspend fun getCourse(): List<Int> {
        return scheduleApi.getCourse().courses
    }

    override suspend fun getGroup(program: Program, course: Course): List<Group> {
        return scheduleApi.getGroup(
            program = program.name,
            course = course.number
        ).groups
    }

    override suspend fun getProgram(course: Course): List<Program> {
        return scheduleApi.getProgram(
            course = course.number
        ).programs
    }
}