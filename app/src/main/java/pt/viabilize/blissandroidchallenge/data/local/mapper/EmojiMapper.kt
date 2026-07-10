package pt.viabilize.blissandroidchallenge.data.local.mapper

import pt.viabilize.blissandroidchallenge.data.local.model.EmojiEntity
import pt.viabilize.blissandroidchallenge.model.Emoji

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