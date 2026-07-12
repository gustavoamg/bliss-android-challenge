package pt.blissapps.blissandroidchallenge.data.local.mapper

import pt.blissapps.blissandroidchallenge.data.local.model.EmojiEntity
import pt.blissapps.blissandroidchallenge.model.Emoji

fun EmojiEntity.toEmoji() : Emoji {
    return Emoji(
        name = this.name,
        url = this.url
    )
}

fun Emoji.toEmojiEntity() : EmojiEntity{
    return EmojiEntity(
        name = this.name,
        url = this.url
    )
}