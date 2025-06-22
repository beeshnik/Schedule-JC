package hph.app.data.remote.api

import hph.app.data.remote.model.CourseResponse
import hph.app.data.remote.model.GroupResponse
import hph.app.data.remote.model.ProgramResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleApi {
//    @GET("schedule/schedule?group={group}")
//    suspend fun getSchedule(@Path("group") group: String): ScheduleResponse

    @GET("available_courses")
    suspend fun getCourse(): CourseResponse

    @GET("available_programs?course={course}")
    suspend fun getProgram(@Path("course") course: Int): ProgramResponse

    @GET("available_groups?course={course}&program={program}")
    suspend fun getGroup(
        @Path("course") course: Int,
        @Path("program") program: String
    ): GroupResponse

}