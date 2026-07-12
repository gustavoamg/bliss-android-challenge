package pt.blissapps.blissandroidchallenge.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarResponse (
    val id: Long,
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String
)