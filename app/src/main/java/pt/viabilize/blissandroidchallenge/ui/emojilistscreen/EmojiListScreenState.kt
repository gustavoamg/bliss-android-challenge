package pt.viabilize.blissandroidchallenge.ui.emojilistscreen

import pt.viabilize.blissandroidchallenge.model.Emoji

data class EmojiListScreenState(
    val isLoading: Boolean = false,
    val emojiList: List<Emoji> = emptyList()
)
