package pt.viabilize.blissandroidchallenge.repository

import pt.viabilize.blissandroidchallenge.model.Avatar
import pt.viabilize.blissandroidchallenge.model.Emoji

interface GitHubRepository {

    suspend fun getEmojiList() : List<Emoji>

    suspend fun findAvatar(username: String) : Avatar
}