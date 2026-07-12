package pt.viabilize.blissandroidchallenge.ui.emojilistscreen

interface EmojiListViewActions {

    data class EmojiClicked(val emojiId: String): EmojiListViewActions
}