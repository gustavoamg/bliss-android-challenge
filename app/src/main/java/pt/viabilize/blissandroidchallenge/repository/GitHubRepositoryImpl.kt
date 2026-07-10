package pt.viabilize.blissandroidchallenge.repository

import android.util.Log
import pt.viabilize.blissandroidchallenge.data.GithubDatasource
import pt.viabilize.blissandroidchallenge.data.local.LocalDatabase
import pt.viabilize.blissandroidchallenge.data.local.dao.EmojiDao
import pt.viabilize.blissandroidchallenge.data.local.mapper.toEmoji
import pt.viabilize.blissandroidchallenge.data.local.mapper.toEmojiEntity
import pt.viabilize.blissandroidchallenge.model.Emoji
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    val dataSourceRemote: GithubDatasource,
    val emojiDatasourceLocal: EmojiDao
) : GitHubRepository {
    val TAG = "GITHUB_REPOSITORY"

    override suspend fun getEmojiList(): List<Emoji> {
        Log.i (TAG, "Loading emoji list")
        val localEmojis = emojiDatasourceLocal.getAll()
        if(localEmojis.isEmpty()) {
            Log.i (TAG, "No local data found. Fetching from GitHub.")
            val response: Map<String, String>? = dataSourceRemote.listEmojis().body()
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
}