package pt.viabilize.blissandroidchallenge.repository

import pt.viabilize.blissandroidchallenge.model.Emoji

interface GitHubRepository {

    suspend fun getEmojiList() : List<Emoji>
}