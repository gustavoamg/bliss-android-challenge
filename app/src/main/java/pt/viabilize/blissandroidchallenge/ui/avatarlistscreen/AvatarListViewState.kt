package pt.viabilize.blissandroidchallenge.ui.avatarlistscreen

import pt.viabilize.blissandroidchallenge.model.Avatar

data class AvatarListViewState(
    val isLoading: Boolean = false,
    val avatarList: List<Avatar> = emptyList()
)
