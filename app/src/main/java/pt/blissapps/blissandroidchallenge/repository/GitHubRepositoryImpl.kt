package pt.blissapps.blissandroidchallenge.repository

import android.util.Log
import kotlinx.serialization.SerializationException
import pt.blissapps.blissandroidchallenge.data.GithubDatasource
import pt.blissapps.blissandroidchallenge.data.exception.AvatarNotFoundException
import pt.blissapps.blissandroidchallenge.data.exception.NetworkException
import pt.blissapps.blissandroidchallenge.data.local.dao.AvatarDao
import pt.blissapps.blissandroidchallenge.data.local.dao.EmojiDao
import pt.blissapps.blissandroidchallenge.data.local.mapper.toAvatar
import pt.blissapps.blissandroidchallenge.data.local.mapper.toEmoji
import pt.blissapps.blissandroidchallenge.data.local.mapper.toEmojiEntity
import pt.blissapps.blissandroidchallenge.data.local.mapper.toEntity
import pt.blissapps.blissandroidchallenge.data.local.mapper.toRepo
import pt.blissapps.blissandroidchallenge.data.local.model.AvatarEntity
import pt.blissapps.blissandroidchallenge.model.Avatar
import pt.blissapps.blissandroidchallenge.model.Emoji
import pt.blissapps.blissandroidchallenge.model.Repo
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    val gitHubDataSourceRemote: GithubDatasource,
    val emojiDatasourceLocal: EmojiDao,
    val avatarDatasourceLocal: AvatarDao
) : GitHubRepository {
    val TAG = "GITHUB_REPOSITORY"

    override suspend fun getEmojiList(): List<Emoji> {
        Log.i (TAG, "Loading emoji list")
        val localEmojis = emojiDatasourceLocal.getAll()
        if(localEmojis.isEmpty()) {
            Log.i (TAG, "No local data found. Fetching from GitHub.")
            val response: Map<String, String>? = gitHubDataSourceRemote.listEmojis().body()
            val emojiList = response?.map { (key, value) ->
                Emoji(key, value)
            } ?: emptyList()

            if(emojiList.isNotEmpty()) {
                val emojiEntities = emojiList.map { it.toEmojiEntity() }
                emojiDatasourceLocal.insertAll(*emojiEntities.toTypedArray())
            }
            return emojiList
        }
        else {
            Log.i (TAG, "Previous local data found. Returning cached results.")
            return localEmojis.map {
                it.toEmoji()
            }
        }
    }

    override suspend fun findAvatar(username: String): Avatar {
        Log.i (TAG, "Looking up for avatar $username in local database")
        val avatarLocal: AvatarEntity? = avatarDatasourceLocal.findByName(username)
        return if(avatarLocal == null) {
            Log.i (TAG, "Avatar not found. Looking up on GitHub")
            val avatarResponse = gitHubDataSourceRemote.searchAvatar(username)
            when (avatarResponse.code()) {
                200 -> {
                    val avatar = avatarResponse.body()?.toAvatar()

                    if (avatar != null) {
                        Log.i(TAG, "Avatar found!")
                        avatarDatasourceLocal.insertAll(avatar.toEntity())
                        Log.i(TAG, "Avatar persisted locally")

                        avatar
                    }
                    else {
                        throw SerializationException("Invalid response!")
                    }
                }
                404 -> throw AvatarNotFoundException()
                else -> throw NetworkException()
            }
        }
        else {
            avatarLocal.toAvatar()
        }
    }

    override suspend fun getAvatarList(): List<Avatar> {
        Log.i (TAG, "Loading avatar list")
        return avatarDatasourceLocal.getAll().map {
            it.toAvatar()
        }
    }

    override suspend fun removeAvatar(avatar: Avatar) {
        avatarDatasourceLocal.delete(avatar.toEntity())
    }

    override suspend fun loadRepos(user: String, page: Int, perPage: Int): List<Repo> {
        Log.i (TAG, "Loading avatar list")
        val repositoriesResponse = gitHubDataSourceRemote.loadRepositories(user, page, perPage)
        when(repositoriesResponse.code()) {
            200 -> {
                return repositoriesResponse.body()?.map { it.toRepo() } ?: emptyList()
            }
            else -> {
                throw NetworkException()
            }
        }
    }
}