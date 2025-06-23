package hph.app.data.repository

import hph.app.data.remote.api.RetrofitClient
import hph.app.data.remote.api.ScheduleApi
import hph.app.domain.model.Lesson
import hph.app.domain.repository.ScheduleRepository

class ScheduleRepositoryImplementation(
    private val scheduleApi: ScheduleApi = RetrofitClient.scheduleApi
): ScheduleRepository {
    override suspend fun getCourse(): List<Int> {
        return scheduleApi.getCourse().courses
    }

    override suspend fun getGroup(program: String, course: Int): List<String> {
        return scheduleApi.getGroup(
            program = program,
            course = course
        ).groups
    }

    override suspend fun getProgram(course: Int): List<String> {
        return scheduleApi.getProgram(
            course = course
        ).programs
    }

    override suspend fun getSchedule(group: String): List<Lesson> {
        return scheduleApi.getSchedule(
            group = group
        ).lessons
    }
}