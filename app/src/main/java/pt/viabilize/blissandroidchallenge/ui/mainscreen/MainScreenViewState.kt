package pt.viabilize.blissandroidchallenge.ui.mainscreen

import pt.viabilize.blissandroidchallenge.model.Avatar

data class MainScreenViewState (
    val isLoading: Boolean = false,
    val imageUrl: String? = null,
    val isSearching: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<Avatar> = emptyList()
)