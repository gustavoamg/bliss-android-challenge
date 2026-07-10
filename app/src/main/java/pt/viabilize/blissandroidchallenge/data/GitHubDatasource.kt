package pt.viabilize.blissandroidchallenge.data

import retrofit2.Response
import retrofit2.http.GET

const val EMOJI_API_BASE_URL:String = "https://api.github.com"

interface GithubDatasource {

    @GET("emojis")
    suspend fun listEmojis (): Response<Map<String, String>>
}