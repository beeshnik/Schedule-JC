package hph.app.data.remote.model

import com.google.gson.annotations.SerializedName
import hph.app.domain.model.Lesson
import java.time.DayOfWeek
import java.time.LocalDate

data class ScheduleResponse(
    @SerializedName("error") val isError: Boolean,
    @SerializedName("response") val lessons: List<Lesson>
)


//data class ScheduleResponse(
//    @SerializedName("error")
//    val isError: Boolean,
//
//    @SerializedName("response")
//    val lessons: List<LessonResponse>
//) {
//    data class LessonResponse(
//        @SerializedName("subject")
//        val subject: String,
//
//        @SerializedName("subGroup")
//        val subGroup: Int?,
//
//        @SerializedName("time")
//        val time: TimeResponse,
//
//        @SerializedName("lecturer")
//        val lecturer: String?,
//
//        @SerializedName("places")
//        val places: List<PlaceResponse>?,
//
//        @SerializedName("links")
//        val links: List<String>?,
//
//        @SerializedName("additionalInfo")
//        val additionalInfo: List<String>?,
//
//        @SerializedName("lessonType")
//        val lessonType: String,
//
//        @SerializedName("parentScheduleType")
//        val parentScheduleType: String,
//
//        @SerializedName("isOnline")
//        val isOnline: Boolean?
//    ) {
//        data class TimeResponse(
//            @SerializedName("dayOfWeek")
//            val dayOfWeek: String,
//
//            @SerializedName("date")
//            val date: String, // Формат "dd.MM.yyyy"
//
//            @SerializedName("startTime")
//            val startTime: String,
//
//            @SerializedName("endTime")
//            val endTime: String
//        )
//
//        data class PlaceResponse(
//            @SerializedName("office")
//            val office: String?,
//
//            @SerializedName("building")
//            val building: Int?
//        )
//    }
//}
