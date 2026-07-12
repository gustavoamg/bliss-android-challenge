package pt.blissapps.blissandroidchallenge.ui.avatarlistscreen

import pt.blissapps.blissandroidchallenge.model.Avatar

data class AvatarListViewState(
    val isLoading: Boolean = false,
    val avatarList: List<Avatar> = emptyList()
)
