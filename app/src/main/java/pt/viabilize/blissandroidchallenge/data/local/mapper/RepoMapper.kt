package pt.viabilize.blissandroidchallenge.data.local.mapper

import pt.viabilize.blissandroidchallenge.data.remote.RepositoriesResponse
import pt.viabilize.blissandroidchallenge.model.Repo

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