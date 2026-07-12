package pt.viabilize.blissandroidchallenge.data

import pt.viabilize.blissandroidchallenge.data.remote.AvatarResponse
import pt.viabilize.blissandroidchallenge.data.remote.RepositoriesResponse
import pt.viabilize.blissandroidchallenge.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val EMOJI_API_BASE_URL:String = "https://api.github.com"

interface GithubDatasource {

    @GET("emojis")
    suspend fun listEmojis (): Response<Map<String, String>>

    @GET("users/{username}")
    suspend fun searchAvatar(@Path("username") username: String ) : Response<AvatarResponse>

    @GET("users/{username}/repos")
    suspend fun loadRepositories(@Path("username") username:String, @Query("page") page:Int, @Query("per_page") perPage:Int) : Response<List<RepositoriesResponse>>

}