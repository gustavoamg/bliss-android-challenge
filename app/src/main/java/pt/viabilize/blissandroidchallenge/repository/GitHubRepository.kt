package pt.viabilize.blissandroidchallenge.repository

import pt.viabilize.blissandroidchallenge.model.Avatar
import pt.viabilize.blissandroidchallenge.model.Emoji
import pt.viabilize.blissandroidchallenge.model.Repo

interface GitHubRepository {

    suspend fun getEmojiList() : List<Emoji>

    suspend fun findAvatar(username: String) : Avatar

    suspend fun getAvatarList() : List<Avatar>

    suspend fun removeAvatar(avatar: Avatar)

    suspend fun loadRepos(user:String, page: Int, perPage: Int): List<Repo>
}