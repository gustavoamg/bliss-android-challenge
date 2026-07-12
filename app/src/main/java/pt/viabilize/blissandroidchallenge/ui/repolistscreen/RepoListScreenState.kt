package pt.viabilize.blissandroidchallenge.ui.repolistscreen

import pt.viabilize.blissandroidchallenge.model.Repo

data class RepoListScreenState(
    val isLoading: Boolean = false,
    val repoList: List<Repo> = emptyList()
)