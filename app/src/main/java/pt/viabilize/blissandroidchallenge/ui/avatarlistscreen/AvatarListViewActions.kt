package pt.viabilize.blissandroidchallenge.ui.avatarlistscreen

interface AvatarListViewActions {
    data class AvatarClicked(val avatarId: Long) : AvatarListViewActions
}