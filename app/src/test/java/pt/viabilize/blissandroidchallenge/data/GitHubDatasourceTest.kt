package pt.viabilize.blissandroidchallenge.data

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import pt.viabilize.blissandroidchallenge.util.readResourceFile
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


class GitHubDatasourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: GithubDatasource

    private val json = Json {
        ignoreUnknownKeys = true // Ignora campos extras vindos da API
        coerceInputValues = true // Trata valores nulos incompatíveis graciosamente
        encodeDefaults = true    // Inclui valores padrão na serialização
    }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val contentType = "application/json; charset=UTF-8".toMediaType()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/")) // Redireciona as chamadas para o servidor de teste
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(GithubDatasource::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_should_parse_response_successfully() {
        val jsonContent = readResourceFile("emoji_response.json", javaClass.classLoader)
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(jsonContent)

        mockWebServer.enqueue(mockResponse)

        val response = runBlocking {
             apiService.listEmojis()
        }

        assertNotNull(response)
        val responseBody = response.body()
        assertNotNull(responseBody)
        assertEquals("https://github.githubassets.com/images/icons/emoji/unicode/1f522.png?v8", responseBody?.get("1234"))

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/emojis", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)
    }

    @Test
    fun test_should_parse_avatar_response_successfully() {
        val jsonContent = readResourceFile("avatar_response.json", javaClass.classLoader)
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(jsonContent)

        mockWebServer.enqueue(mockResponse)

        val response = runBlocking {
            apiService.searchAvatar("any_user")
        }

        assertNotNull(response)
        val responseBody = response.body()
        assertNotNull(responseBody)
        assertEquals(223156L, responseBody?.id)
        assertEquals("blissapps", responseBody?.login)
        assertEquals("https://avatars.githubusercontent.com/u/223156?v=4", responseBody?.avatarUrl)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/users/any_user", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)
    }

    @Test
    fun test_should_parse_repos_response_successfully() {
        val jsonContent = readResourceFile("repos_response.json", javaClass.classLoader)
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(jsonContent)

        mockWebServer.enqueue(mockResponse)

        val response = runBlocking {
            apiService.loadRepositories("any_user", 1, 5)
        }

        assertNotNull(response)
        val responseBody = response.body()
        assertNotNull(responseBody)
        assertEquals(5, responseBody?.size)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/users/any_user/repos?page=1&per_page=5", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)
    }
}