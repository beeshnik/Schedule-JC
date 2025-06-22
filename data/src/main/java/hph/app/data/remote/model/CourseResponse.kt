package hph.app.data.remote.model

import com.google.gson.annotations.SerializedName
import hph.app.domain.model.Course

data class CourseResponse(
    @SerializedName("error") val isError: Boolean,
    @SerializedName("response") val courses: List<Int>
)