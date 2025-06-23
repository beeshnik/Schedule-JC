package hph.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class GroupResponse(
    @SerializedName("error") val isError: Boolean,
    @SerializedName("response") val groups: List<String>
)