package hph.app.data.remote.model

import com.google.gson.annotations.SerializedName
import hph.app.domain.model.Group

data class GroupResponse(
    @SerializedName("error") val isError: Boolean,
    @SerializedName("response") val groups: List<Group>
)