package pt.viabilize.blissandroidchallenge.ui.mainscreen

import pt.viabilize.blissandroidchallenge.model.Emoji

data class MainScreenViewState (
    val emojiList: List<Emoji> = emptyList(),
    val isLoading: Boolean = false
)