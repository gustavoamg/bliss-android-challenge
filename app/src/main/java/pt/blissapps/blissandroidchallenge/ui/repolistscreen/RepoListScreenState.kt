package pt.blissapps.blissandroidchallenge.ui.repolistscreen

import pt.blissapps.blissandroidchallenge.model.Repo

data class RepoListScreenState(
    val isLoading: Boolean = false,
    val repoList: List<Repo> = emptyList()
)