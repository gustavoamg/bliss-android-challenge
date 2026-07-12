package pt.viabilize.blissandroidchallenge.ui.mainscreen

interface MainScreenActions {

    data object RandomEmojiButtonAction : MainScreenActions

    data object EmojiListButtonClick : MainScreenActions

    data class SearchAvatar(val username: String) : MainScreenActions

    data object AvatarListButtonClick : MainScreenActions
}