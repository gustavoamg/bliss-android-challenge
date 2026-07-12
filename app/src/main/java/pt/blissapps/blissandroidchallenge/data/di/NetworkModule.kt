package pt.blissapps.blissandroidchallenge.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import pt.blissapps.blissandroidchallenge.data.EMOJI_API_BASE_URL
import pt.blissapps.blissandroidchallenge.data.GithubDatasource
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true // Ignora campos extras vindos da API
            coerceInputValues = true // Trata valores nulos incompatíveis graciosamente
            encodeDefaults = true    // Inclui valores padrão na serialização
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideEmojiDatasource(json: Json, okHttpClient: OkHttpClient) : GithubDatasource {
        val contentType = "application/json; charset=UTF-8".toMediaType()
        return Retrofit.Builder()
            .baseUrl(EMOJI_API_BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(GithubDatasource::class.java)
    }
}