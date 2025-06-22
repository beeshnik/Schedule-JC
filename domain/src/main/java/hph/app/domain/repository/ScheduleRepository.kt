package hph.app.domain.repository

import hph.app.domain.model.Course
import hph.app.domain.model.Group
import hph.app.domain.model.Program

interface ScheduleRepository {
    suspend fun getCourse(): List<Int>
    suspend fun getGroup(program: Program, course: Course): List<Group>
    suspend fun getProgram(course: Course): List<Program>
}