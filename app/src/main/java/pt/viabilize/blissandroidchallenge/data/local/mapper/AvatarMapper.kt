package pt.viabilize.blissandroidchallenge.data.local.mapper

import pt.viabilize.blissandroidchallenge.data.local.model.AvatarEntity
import pt.viabilize.blissandroidchallenge.data.remote.AvatarResponse
import pt.viabilize.blissandroidchallenge.model.Avatar

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