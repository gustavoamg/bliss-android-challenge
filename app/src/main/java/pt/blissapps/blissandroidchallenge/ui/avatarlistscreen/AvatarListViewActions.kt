package pt.blissapps.blissandroidchallenge.ui.avatarlistscreen

interface AvatarListViewActions {
    data class AvatarClicked(val avatarId: Long) : AvatarListViewActions
}