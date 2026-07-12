package pt.blissapps.blissandroidchallenge.model

import java.util.Date

data class Repo(
    val id: Long,
    val name: String,
    val description: String,
    val updatedAt: Date,
    val watchersCount: Int = 0,
    val forks: Int = 0
)
