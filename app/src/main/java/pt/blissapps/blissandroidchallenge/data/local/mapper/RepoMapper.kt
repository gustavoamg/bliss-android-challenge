package pt.blissapps.blissandroidchallenge.data.local.mapper

import pt.blissapps.blissandroidchallenge.data.remote.RepositoriesResponse
import pt.blissapps.blissandroidchallenge.model.Repo

fun RepositoriesResponse.toRepo(): Repo {
    return Repo(
        id = this.id,
        name = this.name,
        description = this.description ?: "",
        updatedAt = this.updatedAt,
        forks = this.forks,
        watchersCount = this.watchersCount
    )
}