package hph.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProgramResponse(
    @SerializedName("error") val isError: Boolean,
    @SerializedName("response") val programs: List<String>
)