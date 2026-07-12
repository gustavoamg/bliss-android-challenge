package pt.blissapps.blissandroidchallenge.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pt.blissapps.blissandroidchallenge.data.util.DateSerializer
import java.util.Date

@Serializable
data class RepositoriesResponse (
    val id: Long,
    val name: String,
    val description: String? = null,
    @Serializable(with = DateSerializer::class)
    @SerialName("updated_at") val updatedAt: Date,
    @SerialName("watchers_count") val watchersCount: Int = 0,
    val forks: Int = 0
)