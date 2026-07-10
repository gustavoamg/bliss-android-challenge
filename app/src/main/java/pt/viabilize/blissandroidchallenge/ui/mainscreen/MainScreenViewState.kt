package pt.viabilize.blissandroidchallenge.ui.mainscreen

import pt.viabilize.blissandroidchallenge.model.Emoji

data class MainScreenViewState (
    val isLoading: Boolean = false,
    val currentEmoji: Emoji? = null
)