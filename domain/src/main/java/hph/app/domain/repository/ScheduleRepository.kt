package hph.app.domain.repository

import hph.app.domain.model.Lesson

interface ScheduleRepository {
    suspend fun getCourse(): List<Int>
    suspend fun getGroup(program: String, course: Int): List<String>
    suspend fun getProgram(course: Int): List<String>
    suspend fun getSchedule(group: String): List<Lesson>
}