package pt.viabilize.blissandroidchallenge.data

import pt.viabilize.blissandroidchallenge.data.remote.AvatarResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

const val EMOJI_API_BASE_URL:String = "https://api.github.com"

interface GithubDatasource {

    @GET("emojis")
    suspend fun listEmojis (): Response<Map<String, String>>

    @GET("users/{username}")
    suspend fun searchAvatar(@Path("username") username: String ) : Response<AvatarResponse>

}