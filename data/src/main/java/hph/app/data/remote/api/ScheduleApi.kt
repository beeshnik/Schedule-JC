package hph.app.data.remote.api

import hph.app.data.remote.model.CourseResponse
import hph.app.data.remote.model.GroupResponse
import hph.app.data.remote.model.ProgramResponse
import hph.app.data.remote.model.ScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {
    @GET("schedule")
    suspend fun getSchedule(
        @Query("group") group: String
    ): ScheduleResponse

    @GET("schedule/available_courses")
    suspend fun getCourse(): CourseResponse

    @GET("schedule/available_programs")
    suspend fun getProgram(
        @Query("course") course: Int
    ): ProgramResponse

    @GET("schedule/available_groups")
    suspend fun getGroup(
        @Query("course") course: Int,
        @Query("program") program: String
    ): GroupResponse

}