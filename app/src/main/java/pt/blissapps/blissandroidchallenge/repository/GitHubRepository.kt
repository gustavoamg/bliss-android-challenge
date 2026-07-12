package pt.blissapps.blissandroidchallenge.repository

import pt.blissapps.blissandroidchallenge.model.Avatar
import pt.blissapps.blissandroidchallenge.model.Emoji
import pt.blissapps.blissandroidchallenge.model.Repo

interface GitHubRepository {

    suspend fun getEmojiList() : List<Emoji>

    suspend fun findAvatar(username: String) : Avatar

    suspend fun getAvatarList() : List<Avatar>

    suspend fun removeAvatar(avatar: Avatar)

    suspend fun loadRepos(user:String, page: Int, perPage: Int): List<Repo>
}