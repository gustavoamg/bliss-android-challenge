package pt.blissapps.blissandroidchallenge.data.local.mapper

import pt.blissapps.blissandroidchallenge.data.local.model.AvatarEntity
import pt.blissapps.blissandroidchallenge.data.remote.AvatarResponse
import pt.blissapps.blissandroidchallenge.model.Avatar

fun Avatar.toEntity() : AvatarEntity {
    return AvatarEntity(
        id = this.id,
        login =  this.login,
        avatarUrl = this.avatarUrl
    )
}

fun AvatarEntity.toAvatar() : Avatar {
    return Avatar(
        id = this.id,
        login =  this.login,
        avatarUrl = this.avatarUrl
    )
}

fun AvatarResponse.toAvatar() : Avatar {
    return Avatar(
        id = this.id,
        login =  this.login,
        avatarUrl = this.avatarUrl
    )
}