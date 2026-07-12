package pt.blissapps.blissandroidchallenge.ui.emojilistscreen

import pt.blissapps.blissandroidchallenge.model.Emoji

data class EmojiListScreenState(
    val isLoading: Boolean = false,
    val emojiList: List<Emoji> = emptyList()
)
